
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/getimage")
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1252164443969770001L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String filename = request.getParameter( "filename" );
        if ( filename == null ) return;
        String fullPath = getServletContext().getRealPath(Shared.IMAGES_FOLDER)
                        + File.separator + filename;
        
        File srcFile = new File( fullPath );
        byte [] buffer = new byte[(int)srcFile.length()];
        try ( FileInputStream inputStream = new FileInputStream(srcFile)) {
            inputStream.read(buffer);
        }
        response.setContentType( "image/png" );
        try {
            Cipher cipher = Cipher.getInstance( "AES" );
            cipher.init( Cipher.DECRYPT_MODE, Shared.SECRET_KEY );
            byte [] imageBytes = cipher.doFinal( buffer );
            try ( OutputStream outputStream = response.getOutputStream() ) {
                outputStream.write( imageBytes );
            }
        } catch( Exception exception ) {
            exception.printStackTrace();
        }

    }
    
}