<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="../tiles/templates/css.jsp"></jsp:include>
  <title>Página de Login | </title>

  <!-- Favicons-->
  <link rel="icon" href="resources/images/favicon/favicon-32x32.png" sizes="32x32">
  <!-- Favicons-->
  <link rel="apple-touch-icon-precomposed" href="/images/favicon/apple-touch-icon-152x152.png">
  <!-- For iPhone -->
  <meta name="msapplication-TileColor" content="#00bcd4">
  <meta name="msapplication-TileImage" content="/images/favicon/mstile-144x144.png">
  <!-- For Windows Phone -->

  <!-- CORE CSS-->

  <link href="resources/css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">

  <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
  <link href="resources/css/prism.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="resources/js/plugins/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet" media="screen,projection">
  
  
  
</head>

<body class="cyan">
  <!-- Start Page Loading -->
  <div id="loader-wrapper">
      <div id="loader"></div>        
      <div class="loader-section section-left"></div>
      <div class="loader-section section-right"></div>
  </div>
  <!-- End Page Loading -->

  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
      <form class="login-form" action="login" method="post" id="loginForm">
        <div class="row">
          <div class="input-field col s12 center">
            <img src="resources/images/login-logo.png" alt="" class="circle responsive-img valign profile-image-login">
            <p class="center login-form-text">Projeto Portfólio</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-social-person-outline prefix"></i>
            <input id="login" name="login" type="text" class="required">
            <div class="input-field col s2"></div>
            <label for="login" >Login</label>
          </div>
        </div>
	
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="senha" name="senha" type="password" class="required">
            <div class="input-field col s2"></div>
            <label for="senha">Senha</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <button type="submit" onclick="send()" class="btn waves-effect waves-light col s12">Login</button>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s6 m6 l6">
            <p class="margin medium-small"><a href="public/registro">Registrar-se Agora</a></p>
          </div>
        </div>
      </form>
    </div>
  </div>
  <!-- ================================================
    Scripts
    ================================================ -->

	<jsp:include page="../tiles/templates/js.jsp"></jsp:include>
	<script type="text/javascript" src="/resources/js/pages/login.js"></script>
	
	 <c:if test="${param.error ne null}">
	   	<script>
	   		Materialize.toast('Login ou senha invalida!', 4000, 'red') 
	   	</script>
	</c:if>
</body>
</html>