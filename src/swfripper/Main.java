package swfripper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 16/09/12
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static String furnidata;
    static String productdata;
    static String external_variables;
    static String external_flash_texts;
    static String gordon;
    static String hof_furni;
    static String c_images;
    static boolean download_icon_catalogue;
    static boolean download_gordon;
    static boolean download_mp3;
    static boolean continuedownload = true;
    static boolean download_furniture;
    static boolean download_badges;
    static Properties config = Configuration.lerConfig();
    static File pasta1 = new File("DOWNLOADS");
    static int filedownloadeds = 0;
    static BufferedReader bf1;
    static BufferedReader bf2;
    static ArrayList<String> badgesList = new ArrayList<String>();

    public static void main(String[] args) {
        System.out.println("SWFRipper - programado por jomp16");
        System.out.println();
        if (!Configuration.isworking) {
            System.out.println("Nao foi possivel abrir o SWFRipper :(");
        } else {
            furnidata = config.getProperty("furnidata");
            productdata = config.getProperty("productdata");
            external_flash_texts = config.getProperty("external_flash_texts");
            external_variables = config.getProperty("external_variables");
            gordon = config.getProperty("gordon");
            hof_furni = config.getProperty("hof_furni");
            c_images = config.getProperty("c_images");
            download_mp3 = "true".equals(config.getProperty("download_mp3"));
            download_gordon = "true".equals(config.getProperty("download_gordon"));
            download_furniture = "true".equals(config.getProperty("download_furniture"));
            download_icon_catalogue = "true".equals(config.getProperty("download_icon_catalogue"));
            download_badges = "true".equals(config.getProperty("download_badges"));
            if (!pasta1.exists()) {
                boolean success = pasta1.mkdirs();
                if (!success) {
                    System.out.println("Erro ao criar pasta para armazenar os downloads");
                } else {
                    //System.out.println("Criado pasta DOWNLOADS");
                }
            }
            Scanner sc = new Scanner(System.in);
            if (pasta1.exists()) {
                System.out.println("Escreva o nome da pasta que voce quer guardar os downloads");
                String RELEASE_NAME = sc.nextLine();
                boolean success1 = (new File("DOWNLOADS/" + RELEASE_NAME)).mkdirs();
                boolean success2 = (new File("DOWNLOADS/" + RELEASE_NAME + "/localgordon")).mkdirs();
                boolean success3 = (new File("DOWNLOADS/" + RELEASE_NAME + "/gamedata")).mkdirs();
                boolean success7 = (new File("DOWNLOADS/" + RELEASE_NAME + "/dcr")).mkdirs();
                boolean success4 = (new File("DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni")).mkdirs();
                boolean success5 = (new File("DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/mp3")).mkdirs();
                boolean success6 = (new File("DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/ccts")).mkdirs();
                boolean success8 = (new File("DOWNLOADS/" + RELEASE_NAME + "/c_images")).mkdirs();
                boolean success9 = (new File("DOWNLOADS/" + RELEASE_NAME + "/c_images/catalogue")).mkdirs();
                boolean sucess10 = (new File("DOWNLOADS/" + RELEASE_NAME + "/c_images/album1584")).mkdirs();
                if (success1 && success2 && success3 && success4 && success5 && success6 && success7 && success8 && success9 && sucess10) {
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/localgordon");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/gamedata");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/dcr");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/mp3");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/ccts");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/c_images");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/c_images/catalogue");
                    //System.out.println("Criado pasta: DOWNLOADS/" + RELEASE_NAME + "/c_images/album1584");
                    System.out.println("Foram criados as pastas necessarias");
                    System.out.println();
                } else {
                    System.out.println("Não foi possivel criar as pastas");
                }
                startDownload(RELEASE_NAME);
                System.out.println("Baixados: " + filedownloadeds + " arquivos");
                System.out.println("Aperte alguma tecla para fechar...");
                sc.nextLine();
                System.exit(0);
            }
        }
    }

    public static void startDownload(String RELEASE_NAME) {

        /*
        *   config_habbo.xml
        */

        if (download_gordon == true) {
            try {
                downloadFile(gordon + "config_habbo.xml", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "config_habbo.xml");
                bf1 = new BufferedReader(new FileReader("DOWNLOADS/" + RELEASE_NAME + "/localgordon/config_habbo.xml"));
                String teste1; // = bf1.readLine().replaceAll("<library url=", "");
                while ((teste1 = bf1.readLine()) != null) {
                    String teste2 = teste1.replaceAll("        <library url=\"", "");
                    String teste3 = teste2.replaceAll("\" />", "");
                    if (teste3.contains("hh")) {
                        downloadFile(gordon + teste3, "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", teste3);
                    }
                }
            } catch (IOException e) {
                System.out.println("Não foi possível baixar o config_habbo.xml");
            }

            /*
            *   figuremap.xml
            */

            try {
                downloadFile(gordon + "figuremap.xml", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "figuremap.xml");
                bf1 = new BufferedReader(new FileReader("DOWNLOADS/" + RELEASE_NAME + "/localgordon/figuremap.xml"));
                String teste1;
                while ((teste1 = bf1.readLine()) != null) {
                    String teste2 = teste1.replaceAll("  <lib id=\"", "");
                    String teste3 = teste2.replaceAll("\" revision=\"", ".swf");
                    String teste4 = teste3.replaceAll("<map>", "   <map>");
                    String teste5 = teste4.replaceAll("</map>", "    </map>");
                    String teste6 = teste5.replaceAll("  </lib>", "  </lib>");
                    String teste7 = teste6.substring(0, teste6.length() - 7);
                    if (teste7.toLowerCase().contains("jacket") || teste7.toLowerCase().contains("shirt") || teste7.toLowerCase().contains("shoes") || teste7.toLowerCase().contains("trousers") || teste7.toLowerCase().contains("hair") || teste7.toLowerCase().contains("acc") || teste7.toLowerCase().contains("hat") || teste7.toLowerCase().contains("hh")) {
                        downloadFile(gordon + teste7, "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", teste7);
                    }
                }
            } catch (IOException e) {
                System.out.println("Não foi possível baixar o figuredata.xml");
            }

            /*
            *   Other .swf files
            */

            downloadFile(gordon + "pets_palettes.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "pets_palettes.swf");
            downloadFile(gordon + "PlaceHolderFurniture.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "PlaceHolderFurniture.swf");
            downloadFile(gordon + "PlaceHolderPet.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "PlaceHolderPet.swf");
            downloadFile(gordon + "PlaceHolderWallItem.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "PlaceHolderWallItem.swf");
            downloadFile(gordon + "SelectionArrow.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "SelectionArrow.swf");
            downloadFile(gordon + "TileCursor.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "TileCursor.swf");
            downloadFile(gordon + "Habbo.swf", "DOWNLOADS/" + RELEASE_NAME + "/localgordon/", "Habbo.swf");
        }

        /*
         *   Gamedata
         */

        downloadFile(external_flash_texts, "DOWNLOADS/" + RELEASE_NAME + "/gamedata/", "external_flash_texts.txt");
        downloadFile(external_variables, "DOWNLOADS/" + RELEASE_NAME + "/gamedata/", "external_variables.txt");
        downloadFile(productdata, "DOWNLOADS/" + RELEASE_NAME + "/gamedata/", "productdata.txt");
        downloadFile(furnidata, "DOWNLOADS/" + RELEASE_NAME + "/gamedata/", "furnidata.txt");

        /*
         *   MP3
         */

        if (download_mp3) {
            int MP3 = 0;
            while (continuedownload) {
                downloadFile(hof_furni + "mp3/sound_machine_sample_" + MP3 + ".mp3", "DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/mp3/", "sound_machine_sample_" + MP3 + ".mp3");
                MP3++;
                if (!continuedownload) {
                    break;
                }
            }
        }

        /*
         *   Furniture - thanks to cecer1 to this algoritm ;)
         */

        if (download_furniture) {
            String fileContents = "";
            String lineBuffer;
            try {
                BufferedReader bf1 = new BufferedReader(new FileReader("DOWNLOADS/" + RELEASE_NAME + "/gamedata/furnidata.txt"));
                while ((lineBuffer = bf1.readLine()) != null) {
                    fileContents += lineBuffer;
                }
                bf1.close();

                String[] hofFurniFiles1 = fileContents.split("\\[\\\"");
                for (String e : hofFurniFiles1) {
                    if (e.length() < 4) {
                        continue;
                    }

                    String[] hofFurniFiles2 = e.split("\\\"\\]\\,\\[\\\"");
                    for (String f : hofFurniFiles2) {
                        if (f.length() < 4) {
                            continue;
                        }

                        String[] parts = f.split("\\\"");
                        String FileName = parts[4].split("\\*")[0];
                        String numberFolder = parts[6].split("\\*")[0];
                        downloadFile(hof_furni + numberFolder + "/" + FileName + ".swf", "DOWNLOADS/" + RELEASE_NAME + "/dcr/hof_furni/", FileName + ".swf");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        /*
         *   Catalogue icons
         */

        if (download_icon_catalogue) {
            int ICON = 1;
            if (continuedownload) {
                while (true) {
                    downloadFile(c_images + "catalogue/icon_" + ICON + ".png", "DOWNLOADS/" + RELEASE_NAME + "/c_images/catalogue/", "icon_" + ICON + ".png");
                    ICON++;
                    if (!continuedownload && ICON == 165) {
                        break;
                    }
                }
            }
        }

        /*
        * Badges ripper
         */

        if (download_badges) {
            String fileContent = "";
            String lineBuffer;
            try {
                bf1 = new BufferedReader(new FileReader(("DOWNLOADS/" + RELEASE_NAME + "/gamedata/external_flash_texts.txt")));
                while ((lineBuffer = bf1.readLine()) != null) {
                    if (lineBuffer.contains("badge_name_")) {
                        int index = lineBuffer.indexOf("badge_name_");
                        int num3 = lineBuffer.indexOf("=");
                        if (!lineBuffer.contains("badge_name_fb")) {
                            String item = lineBuffer.substring(index + 11, (num3 - index));
                            if ((item != null) || (item != "")) {
                                downloadFile(c_images + "album1584/" + item + ".gif", "DOWNLOADS/" + RELEASE_NAME + "/c_images/album1584/", item + ".gif");
                                // System.out.println(item);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Deprecated
    public static void downloadFile1(String urla, String diretorio, String file) {
        File f = new File(diretorio + file);
        if (!f.exists()) {
            try {
                URL url1 = new URL(urla);
                System.out.println("Baixando arquivo: " + urla);
                HttpURLConnection uc = (HttpURLConnection) url1.openConnection();
                uc.connect();
                BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(diretorio + file)));
                byte[] buf = new byte[256];
                int n = 0;
                while ((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }
                out.flush();
                out.close();
                uc.disconnect();
                System.out.println("Baixado arquivo: " + urla);
                filedownloadeds++;
            } catch (Exception e) {
                continuedownload = false;
                System.out.println(e.getMessage());
                // System.out.println("Não foi possível baixar o arquivo: " + urla);
            }
        } else {
            System.out.println("Pulando o download desse arquivo " + file + " pois existe na pasta destino");
        }
    }

    public static void downloadFile(String urla, String diretorio, String file) {
        File f = new File(diretorio + file);
        if (!f.exists()) {
            try {
                URL url1 = new URL(urla);
                System.out.println("Baixando arquivo: " + urla);
                InputStream in = url1.openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(diretorio + file)));
                byte[] buf = new byte[2048];
                int n = 0;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                in.close();
                out.close();
                System.out.println("Baixado arquivo: " + urla);
                filedownloadeds++;
            } catch (Exception e) {
                continuedownload = false;
                //System.out.println(e.getMessage());
                System.out.println("Nao foi possivel baixar o arquivo: " + urla);
            }
        } else {
            System.out.println("Pulando o download desse arquivo " + file + " pois existe na pasta destino");
        }
    }
}
