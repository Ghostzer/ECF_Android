<?php
// Routes

$app->group('/api', function () use ($app) {
  // get all regates
  $app->get('/regates',function ($request, $response, $args) {
       $sth = $this->db->prepare("SELECT * FROM regate");
      $sth->execute();
      $movies = $sth->fetchAll();
      return $this->response->withJson($movies);
  });

  //get regate by id
  $app->get('/regate/[{id}]', function ($request, $response, $args) {
         $sth = $this->db->prepare("SELECT * FROM regate");
		 $sth->bindParam("id", $args['id']);
        $sth->execute();
        $moviesById = $sth->fetchObject();
        return $this->response->withJson($moviesById);
    });

      // Add a new todo
    $app->post('/todo', function ($request, $response) {
        $todo = json_decode($request->getBody());
        $sql = "INSERT INTO task (task, priority) VALUES (:task, :priority)";
        $sth = $this->db->prepare($sql);
        $sth->bindParam("task", $todo->task);
        $sth->bindParam("priority", $todo->priority);
        $sth->execute();
        $todo->id=$this->db->lastInsertId();
        //$input['id'] = $this->db->lastInsertId();
        //$this->response->withStatus(201);
        return $this->response->withJson($todo)->withStatus(201);
    });


    // DELETE a todo with given id
    $app->delete('/todo/[{id}]', function ($request, $response, $args) {
         $sth = $this->db->prepare("DELETE FROM task WHERE id=:id");
        $sth->bindParam("id", $args['id']);
        $sth->execute();
        return $this->response->withStatus(204); //no-content
    });


// Update todo with given id
    $app->put('/todo/[{id}]', function ($request, $response, $args) {
        //$input = $request->getParsedBody();
        //$this->logger->debug($request->getBody());
        $todo = json_decode($request->getBody());
        $sql = "UPDATE task SET task=:task,priority=:priority  WHERE id=:id";
         $sth = $this->db->prepare($sql);
        $sth->bindParam("id", $args['id']);
        $sth->bindParam("task", $todo->task);
        $sth->bindParam("priority", $todo->priority);
        $sth->execute();

        //load complete oci_fetch_object
       $sth = $this->db->prepare("SELECT * FROM task WHERE id=:id");
       $sth->bindParam("id", $args['id']);
       $sth->execute();
       $todo = $sth->fetchObject();

        //$input['id'] = $args['id'];
        return $this->response->withJson($todo);
    });





});
