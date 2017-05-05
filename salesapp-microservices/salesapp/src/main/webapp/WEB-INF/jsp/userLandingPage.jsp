<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
<form class="form-inline">  
	<h4>Credit And Customer Info</h4>
  <div class="panel panel-default">
  <div class="panel-heading">Customer Legal Info</div>
  <div class="panel-body">
	<div class="row">
    <div class="col-sm-1">
      <label for="name">Name:</label>
    </div>
    <div class="col-sm-2">
      <input type="name" class="form-control" id="legalName" placeholder="Best Buy">
    </div>
	<div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="city">City:</label>
    </div>
    <div class="col-sm-2">
      <input type="city" class="form-control" id="city" placeholder="Philadelphia">
    </div>
	<div class="col-sm-1"></div>
	<div class="col-sm-1">
      <label for="email">Country:</label>
    </div>
    <div class="col-sm-2">
      <input type="country" class="form-control" id="country" placeholder="USA">
    </div>
 	</div>
	<div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="sAddress">Address:</label>
    </div>
    <div class="col-sm-2">
      <input type="address" class="form-control" id="sAddress" placeholder="123 Race St">
    </div>
	<div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">State:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="Pennsylvania">
    </div>
	<div class="col-sm-1"></div>
	<div class="col-sm-1">
      <label for="zipcode">Zipcode:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="19301">
    </div>
 	</div>
  </div>
</div>

<div class="panel panel-default" style="margin-top:10px;">
  <div class="panel-heading">AT&T Sales Contact Info</div>
  <div class="panel-body">
	<div class="row">
    <div class="col-sm-1">
      <label for="name">Name:</label>
    </div>
    <div class="col-sm-2">
      <input type="name" class="form-control" id="salesName" placeholder="Josh Rodriguez">
    </div>
	<div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="city">Country:</label>
    </div>
    <div class="col-sm-2">
      <input type="city" class="form-control" id="city" placeholder="USA">
    </div>
	<div class="col-sm-1"></div>
	<div class="col-sm-1">
      <label for="email">Manager:</label>
    </div>
    <div class="col-sm-2">
      <input type="country" class="form-control" id="country" placeholder="Chris Michael">
    </div>
 	</div>
	<div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="sAddress">Address:</label>
    </div>
    <div class="col-sm-2">
      <input type="address" class="form-control" id="sAddress" placeholder="123 Race St">
    </div>
	<div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">Telephone:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="484-558-0189">
    </div>
	<div class="col-sm-1"></div>
	<div class="col-sm-1">
      <label for="zipcode">Strata:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="FED">
    </div>
 	</div>
	<div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="sAddress">City:</label>
    </div>
    <div class="col-sm-2">
      <input type="address" class="form-control" id="sAddress" placeholder="Philadelphia">
    </div>
	<div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">Fax:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="484-447-8879">
    </div>
	<div class="col-sm-1"></div>
	<div class="col-sm-1">
      <label for="zipcode">Region:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="US">
    </div>
 	</div>
  </div>
</div>
  <h4>Customer Site Locations</h4>
  <br/>
  <div class="panel panel-default">
  <div class="panel-heading">Headquarters</div>
  <div class="panel-body">
  <div class="row">
  <div class="col-sm-1">
     <label for="hNumber">Street:</label>
   </div>
   <div class="col-sm-2">
     <input type="address" class="form-control" id="sAddress" placeholder="3735">
   </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="city">City:</label>
    </div>
    <div class="col-sm-2">
      <input type="city" class="form-control" id="city" placeholder="Chicago">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="email">Country:</label>
    </div>
    <div class="col-sm-2">
      <input type="country" class="form-control" id="country" placeholder="USA">
    </div>
  </div>
  <div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="name">Address:</label>
    </div>
    <div class="col-sm-2">
      <input type="name" class="form-control" id="legalName" placeholder="W 26th St">
    </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">State:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="Illinois">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="zipcode">Zipcode:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="60623">
    </div>
  </div>

  </div>
</div>
<div class="panel panel-default">
  <div class="panel-heading">Distribution Center</div>
  <div class="panel-body">
  <div class="row">
  <div class="col-sm-1">
     <label for="hNumber">Street:</label>
   </div>
   <div class="col-sm-2">
     <input type="address" class="form-control" id="sAddress" placeholder="1 Erieview Plaza">
   </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="city">City:</label>
    </div>
    <div class="col-sm-2">
      <input type="city" class="form-control" id="city" placeholder="Clevelland">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="email">Country:</label>
    </div>
    <div class="col-sm-2">
      <input type="country" class="form-control" id="country" placeholder="USA">
    </div>
  </div>
  <div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="name">Address:</label>
    </div>
    <div class="col-sm-2">
      <input type="name" class="form-control" id="legalName" placeholder="Suite 1300">
    </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">State:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="Ohio">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="zipcode">Zipcode:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="44114">
    </div>
  </div>

  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">Distribution Center</div>
  <div class="panel-body">
  <div class="row">
  <div class="col-sm-1">
     <label for="hNumber">Street:</label>
   </div>
   <div class="col-sm-2">
     <input type="address" class="form-control" id="sAddress" placeholder="45 Chinmney ct">
   </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="city">City:</label>
    </div>
    <div class="col-sm-2">
      <input type="city" class="form-control" id="city" placeholder="Laurence Harbor">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="email">Country:</label>
    </div>
    <div class="col-sm-2">
      <input type="country" class="form-control" id="country" placeholder="USA">
    </div>
  </div>
  <div class="row" style="margin-top:5px;">
    <div class="col-sm-1">
      <label for="name">Address:</label>
    </div>
    <div class="col-sm-2">
      <input type="name" class="form-control" id="legalName" placeholder="NA">
    </div>
  <div class="col-sm-1"></div> 
    <div class="col-sm-1">
      <label for="state">State:</label>
    </div>
    <div class="col-sm-2">
      <input type="state" class="form-control" id="state" placeholder="New Jersey">
    </div>
  <div class="col-sm-1"></div>
  <div class="col-sm-1">
      <label for="zipcode">Zipcode:</label>
    </div>
    <div class="col-sm-2">
      <input type="zipcode" class="form-control" id="zipcode" placeholder="08879">
    </div>
  </div>

  </div>
</div>


    <div class="row" style="margin-top: 10px;">
      <div class="col-sm-offset-5 col-sm-4">
          <a href="${pageContext.request.contextPath}/user/home" class="btn btn-primary" role="button">Proceed</a>
      </div>
    </div>
  </form>

</div>
</body>
</html>
