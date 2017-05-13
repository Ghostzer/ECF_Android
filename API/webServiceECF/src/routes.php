<?php
// Routes

$app->group('/api', function () use ($app) {
  // get all regates
  $app->get('/regates',function ($request, $response, $args) {
       $sth = $this->db->prepare("SELECT * FROM regate");
      $sth->execute();
      $regates = $sth->fetchAll();
      return $this->response->withJson($regates);
  });

  //get regate by id
  $app->get('/regatebyid/[{id}]', function ($request, $response, $args) {
         $sth = $this->db->prepare("SELECT * FROM regate reg INNER JOIN commissaire com ON reg.id_commissaire=com.id_commissaire INNER JOIN personne per ON com.id_personne=per.id_personne WHERE id_regate=:id");
		 $sth->bindParam("id", $args['id']);
        $sth->execute();
        $regatesById = $sth->fetchObject();
        return $this->response->withJson($regatesById);
    });
	
	//get regate by winter
  $app->get('/regate/winter',function ($request, $response, $args) {
         $sth = $this->db->prepare("SELECT * FROM regate reg 
INNER JOIN commissaire com ON reg.id_commissaire=com.id_commissaire INNER JOIN personne per ON com.id_personne=per.id_personne INNER JOIN challenge cha ON reg.id_challenge=cha.id_challenge WHERE cha.id_challenge = 1");
      $sth->execute();
      $regatesByWinter = $sth->fetchAll();
      return $this->response->withJson($regatesByWinter);
    });
	
	//get regate by summer
  $app->get('/regate/summer',function ($request, $response, $args) {
         $sth = $this->db->prepare("SELECT * FROM regate reg 
INNER JOIN commissaire com ON reg.id_commissaire=com.id_commissaire INNER JOIN personne per ON com.id_personne=per.id_personne INNER JOIN challenge cha ON reg.id_challenge=cha.id_challenge WHERE cha.id_challenge = 2");
      $sth->execute();
      $regatesBySummer = $sth->fetchAll();
      return $this->response->withJson($regatesBySummer);
    });
	
	//get scores by regate
	  $app->get('/resultatsbyregate/[{id}]', function ($request, $response, $args) {
         $sth = $this->db->prepare("SELECT * FROM regate reg INNER JOIN participer par ON reg.id_regate=par.id_regate INNER JOIN voilier voi ON par.id_voilier=voi.id_voilier WHERE reg.id_regate=:id ORDER BY place ASC");
		 $sth->bindParam("id", $args['id']);
      $sth->execute();
      $regates = $sth->fetchAll();
      return $this->response->withJson($regates);
    });


});
