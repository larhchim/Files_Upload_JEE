<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Veuillez choisir les images � uploader.</title>
        
        <script>
            /* Cette fonction permet d'afficher une vignette pour chaque image s�lectionn�e */
            function readFilesAndDisplayPreview(files) {
                let imageList = document.querySelector('#list'); 
                imageList.innerHTML = "";
                
                for ( let file of files ) {
                    let reader = new FileReader();
                    
                    reader.addEventListener( "load", function( event ) {
                        let span = document.createElement('span');
                        span.innerHTML = '<img height="60" src="' + event.target.result + '" />';
                        imageList.appendChild( span );
                    });

                    reader.readAsDataURL( file );
                }
            }
        </script>
    </head>
    <body style="text-align: center">
        
        <header>
            <h1>Veuillez choisir les images � uploader.</h1>
        </header>
        
        <form method="post" action="upload" enctype="multipart/form-data">
            Fichiers s�lectionn�s : 
            <input type="file" name="multiPartServlet" accept="image/*" multiple
                   onchange="readFilesAndDisplayPreview(this.files);" /> <br/>
            <input type="submit" value="Upload" /> <br/>        
            
            <div id="list"></div>   
        </form>
    </body>
</html>