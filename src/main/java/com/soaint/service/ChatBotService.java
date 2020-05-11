package com.soaint.service;

import com.soaint.controller.ChatBotController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.soaint.utils.Colors;
import org.alicebot.ab.*;

import java.io.File;

@Service
public class ChatBotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBotController.class);

    private static final boolean TRACE_MODE = false;
    static String botName = "super";

    public String chatbotService(String answer){

        String response = "";

        try{
            String resourcePath = getResourcePath();

            // Todo: ************* Eliminar esto una vez probada en rest
            logger.info(Colors.ANSI_BLUE +  "Resource Path" + Colors.ANSI_RESET);
            logger.info(Colors.ANSI_BLUE + resourcePath + Colors.ANSI_RESET);
            // *******************

            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcePath);

            logger.info("resourcePath: " + resourcePath);

            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";

            //while(true){
                logger.info("TU: ");

                // se comentó ya que el valor lo entrega rest
                //textLine = IOUtils.readInputTextLine();

                textLine = answer;
                if((textLine == null ) || (textLine.length() < 1)){
                    textLine = MagicStrings.null_input;
                }
                if (textLine.equals("q")) {
                    System.exit(0);
                }else if (textLine.equals("eq")){
                    bot.writeQuit();
                    System.exit(0);
                }
                else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode){
                        logger.info("STATE=" + request + ":THAT=" + (chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    }
                    response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;")) {
                        response = response.replace("&lt;", "<");
                    }//while
                    while (response.contains("&gt;")){
                        response = response.replace("&gt;", ">");
                    }//while

                    // Transform ISO-8859-1 to UTF-8
                    //response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    logger.info("Soniat: " + response);

                }//else
            //}//while(true)

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private String getResourcePath(){

        //TODO:
        //  With this code:    file:/C:/Desarrollo/Test/SoniatV2-1.0.0.jar!/BOOT-INF/classes!/
        //String resourcesPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        //resourcesPath = resourcesPath.substring(0,resourcesPath.length()-1);
        //resourcesPath = "jar:" + resourcesPath;

        //TODO:
        //  With this code:    C:\Desarrollo\Test\src\main\resources
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() -2);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";

        return resourcesPath;
    }

}
