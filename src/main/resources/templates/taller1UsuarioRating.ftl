<!DOCTYPE html>
<html lang="es">
    <head>
    
      <meta charset="utf-8">
  	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Taller1 - GRUPO 11</title>
      <link rel="stylesheet" href="/sr/webjars/bootstrap/4.0.0/css/bootstrap.min.css" />
      <script src="/sr/webjars/jquery/3.1.1/jquery.min.js"></script>
	  <script src="/sr/webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	  
    </head>
    
    <body>
    	<#if errorMessage??>
	      <div style="color:red;font-style:italic;">
	         ${errorMessage}
	      </div>
	    </#if>
	    <div class="container">
            <h2>Taller 1 - Agregar usuarios y ratings</h2>
            <a class="btn btn-warning btn-lg" href="taller1" role="button">VOLVER</a>
        </div>
		<br/>
		<div class="container">
		  <form name="add_user_rating" action="add_user_rating" method="POST">
		    <div class="form-row">

		        <div class="col-md-4 mb-3">
                  <label for="tipoRecomendador">Tipo Recomendador</label>
                  <select name="tipoRecomendador" id="tipoRecomendador" class="custom-select" required>
                    <option value="">Tipo Recomendador ...</option>
                    <option value="canciones">Canciones</option>
                    <option value="artistas">Artistas</option>
                  </select>
                </div>
		        
		        <div class="col-md-4 mb-3">
				   <label for="user">User</label>
				   <input type="text" class="form-control" name="user" id="user" placeholder="User" required>
				</div>

				<div class="col-md-4 mb-3">
                   <label for="item">Item</label>
                   <input type="text" class="form-control" name="item" id="item" placeholder="Item" required>
                </div>

                <div class="col-md-4 mb-3">
                   <label for="rating">Rating</label>
                   <select name="rating" id="rating" class="custom-select" required>
                       <option value="1">1</option>
                       <option value="2">2</option>
                       <option value="3">3</option>
                       <option value="4">4</option>
                       <option value="5">5</option>
                   </select>
                </div>
			</div>
			<button class="btn btn-primary" type="submit">Agregar</button>
		  </form>		
		</div>

    </body>
</html>