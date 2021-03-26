import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/*
 * Notre serlvet permettant de récupérer les fichiers côté serveur.
 * Elle répondra à l'URL /upload dans l'application Web considérée.
 */
@WebServlet( urlPatterns = "/upload" )
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
                  maxFileSize = 1024 * 1024 * 5, 
                  maxRequestSize = 1024 * 1024 * 5 * 5 )
public class UploadServlet extends HttpServlet {

	    public static final String IMAGES_FOLDER = "D:\\CI_GI_2_2021";
	        
	    public String uploadPath;
	    
    private static final long serialVersionUID = 1273074928096412095L;

    @Override
    public void init() throws ServletException {
        File uploadDir = new File( Shared.IMAGES_FOLDER );
        if ( ! uploadDir.exists() ) uploadDir.mkdir();
    }
        
    /*
     * Récupération et sauvegarde du contenu de chaque image.
     * Puis on encrypte le contenu du fichier
     */ 
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse resp ) 
            throws ServletException, IOException {
        for ( Part part : request.getParts() ) {
            String fileName = getFileName( part );
            String fullPath = Shared.IMAGES_FOLDER + File.separator + fileName;
            part.write( fullPath );                 // Ecriture du fichier original
            
            File srcFile = new File( fullPath );    // On récupère ses octets
            byte [] buffer = new byte[ (int) srcFile.length() ];
            try ( FileInputStream inputStream = new FileInputStream( srcFile ) ) {
                inputStream.read( buffer );
            }
    
            try {                                   // On encrypte son contenu avec AES
                Cipher cipher = Cipher.getInstance( "AES" );
                cipher.init( Cipher.ENCRYPT_MODE, Shared.SECRET_KEY );
                try (FileOutputStream outputStream = new FileOutputStream( fullPath ) ) {
                    byte [] outputBytes = cipher.doFinal( buffer );
                    outputStream.write( outputBytes );
                }
            } catch( Exception exception ) {
                exception.printStackTrace();
            }
            
        }
    }

    
    /*
     * Récupération du nom du fichier dans la requête.
     */
    private String getFileName( Part part ) {
        for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( content.trim().startsWith( "filename" ) )
                return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
        }
        return "Default.file";
    }

}