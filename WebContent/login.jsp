<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>登录</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
		<script type="text/javascript" src="js/jquery/jquery-1.11.0.min.js"></script>
		<script type="text/javascript" src="js/bootstrap/bootstrap.js"></script>
		<script type="text/javascript" src="js/pagejs/sysUser.js"></script>
		<script type="text/javascript">
			function register(){
				$("#addUser").modal("show");
				$("#addUser").modal({
					  keyboard: false
				});
			}
		</script>
    </head>
    <body>
        <div class="container">
            <!-- Codrops top bar -->
            <div class="codrops-top">
                <a href="">
                    <strong>&laquo; Previous Demo: </strong>Responsive Content Navigator
                </a>
                <span class="right">
                    <a href="">
                        <strong>Back to the Codrops Article</strong>
                    </a>
                </span>
                <div class="clr"></div>
            </div><!--/ Codrops top bar -->
            <header>
                <h1>AWS<span>助理级架构师习题</span></h1>
				<nav class="codrops-demos">
					<span>AWS Practice Management system</span>
				</nav>
            </header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  action="doLogin" autocomplete="on" method="post"> 
                                <h1>用户登录</h1> 
                                <p> 
                                    <label for="username" class="uname" data-icon="u" > 用户名： </label>
                                    <input id="username" name="username" required="required" type="text" placeholder="请输入你的用户名"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> 密码 ：</label>
                                    <input id="password" name="password" required="required" type="password" placeholder="请输入密码" /> 
                                </p>
                                <p class="keeplogin"> 
									<a onclick="register()">注册</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<label style="color: red;font-size: 20px">${errMsg}</label></label>
								</p>
                                <p class="login button"> 
                                    <input type="submit" value="登录" /> 
								</p>
                                <p class="change_link">
									Have any problem?
									<a href="" class="to_register">Contact us</a>
								</p>
                            </form>
                        </div>
						
                        <div id="register" class="animate form">
                            <form  action="mysuperscript.php" autocomplete="on"> 
                                <h1> Sign up </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                                    <input id="usernamesignup" name="usernamesignup" required="required" type="text" placeholder="mysuperusername690" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
                                    <input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                                    <input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="#tologin" class="to_register"> Go and log in </a>
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
        
        
        <div class="modal fade" data-backdrop="static" id="addUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">用户注册</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="selectTree" class="ztree" style="height:300px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="user/addUser" id="userForm" class="form-horizontal" onsubmit="return false">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="userId" name="id">
				                            <div class="form-group">
				                                <div class="col-lg-5">
													<input id="userTypeU" name="usertype" value="2" hidden="true">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">用户名*</label>
				                                <div class="col-lg-5">
				                                    <input  name="username" id="userNameU" class="form-control" type="text" placeholder="必须唯一" onblur="checkUsername()"/>
				                                </div>
				                                <span id="isenableUsername" style="color: red"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">密码*</label>
				                                <div class="col-lg-5">
				                                    <input id="passwordU1" class="form-control" type="password" placeholder="密码" onblur="checkPassword()"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">确认密码*</label>
				                                <div class="col-lg-5">
				                                    <input  name="password" id="passwordU2" onblur="checkPassword()" class="form-control" type="password" placeholder="再次输入密码"/>
				                                </div>
				                                <span id="passwordError" style="color: red"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">姓名*</label>
				                                <div class="col-lg-5">
											       	<input name="name" id="nameU" class="form-control" type="text" placeholder="姓名" 
											       		 required="required"/>
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addUserSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div>
				    </div>
				</div> 
    </body>
</html>