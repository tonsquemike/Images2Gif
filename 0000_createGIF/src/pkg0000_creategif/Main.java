/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg0000_creategif;

import Funciones.MyListArgs;
import Funciones.MySintaxis;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author UAEM
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // grab the output image type from the first image in the sequence
        String DIR;
        String OUT;
        String files[];
        String   ConfigFile = "";       
        MyListArgs    Param     ;

        Param      = new MyListArgs(args)                  ;
        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");

        if (!ConfigFile.equals("")){
            Param.AddArgsFromFile(ConfigFile);
        }

        String Sintaxis   = "-IMAGES_DIR:str -OUT:str";
        MySintaxis Review = new MySintaxis(Sintaxis, Param);

        DIR               = Param.ValueArgsAsString("-IMAGES_DIR", "");
        OUT               = Param.ValueArgsAsString("-OUT", "");
        files = new File(DIR).list();
        
        try{
            BufferedImage firstImage = ImageIO.read(new File(DIR+File.separator+files[0]));

            // create a new BufferedOutputStream with the last argument
            ImageOutputStream output
                    = new FileImageOutputStream(new File(OUT));

            // create a gif sequence with the type of the first image, 1 second
            // between frames, which loops continuously
            GifSequenceWriter writer
                    = new GifSequenceWriter(output, firstImage.getType(), 1, false);

            // write out the first image to our sequence...
            writer.writeToSequence(firstImage);
            for (int i = 1; i < files.length; i++) {
                BufferedImage nextImage = ImageIO.read(new File(DIR+File.separator+files[i]));
                writer.writeToSequence(nextImage);
            }

            writer.close();
            output.close();
        }catch(Exception e){}
    }
    
}
