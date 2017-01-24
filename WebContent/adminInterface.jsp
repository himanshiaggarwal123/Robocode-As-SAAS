<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

   <title>Admin Interface</title>


   <script>

      function update(id, oldRole){
         var roleSetterId = "roleSetter" + id;
         var userId = "userId" + id;
         var username = document.getElementById(userId).innerText;
         var options = document.getElementById(roleSetterId);
         var newRole = options[options.selectedIndex].text;
         var newUserInfo = {
            "username" : username,
            "password" : "default",
            "location" : location,
            "role" : newRole
         };
   
         var jsonbobj = JSON.stringify(newUserInfo);
         console.log(jsonbobj);
         var getUserURL = "https://cloudcomputing-147420.appspot.com/api/users";
         var completeURL = getUserURL +"/"+username;
         var dataStr = username+";"+oldRole+";"+newRole;
         $.ajax({
            url: "updateRoleACL",
            data: "dataStr="+dataStr, 
            success : function(html) {

               $.ajax({
                  url: completeURL,
                  type: 'PUT',
                  contentType : 'application/json',
                  data: jsonbobj,
                  async : false,
                  success : function(html) {
                  console.log(html);
                  },

                  error : function(data) {
                     console.log(JSON.stringify(data));
                  }
               });  
            }, 
            error : function(data) {
               console.log(JSON.stringify(data));
            }
         });
         event.preventDefault();
         window.location.reload(true);
      }

      function confirmDel(id) {
         var userId = "userId" + id;
         var username = document.getElementById(userId).innerText;
         var r = window.confirm("Are You Sure you want to Delete User " + username) 
   	     if(r == true) {
   		     del(username);
   	     }
      }

      /*
      Uncomment this and comment above to skip popup to confirm user delete
      function confirmDel(id) {
         var userId = "userId" + id;
         var username = document.getElementById(userId).innerText;
         
   		 del(username);
   	     
      }*/
   
      function del(username, role){
         var getUserURL = "https://cloudcomputing-147420.appspot.com/api/users";
         var completeURL = getUserURL +"/"+username;
         var dataStr = username+";"+role;
         $.ajax({
            url: "deleteRoleACL",
            data: dataStr, 
            success : function(html) {

               $.ajax({
                  url: completeURL,
                  type: 'DELETE',
                  async : false,
                  success : function(html) {
                     console.log("Deleted user " + username);
                  },
                  error : function(data) {
                     console.log(JSON.stringify(data));
                  }
               });      
            }, 
            error : function(data) {
               console.log(JSON.stringify(data));
            }
         });
         event.preventDefault();
         window.location.reload(true);
      }

      function userTable(){
         var userIds;
         var getUserURL = "https://cloudcomputing-147420.appspot.com/api/users";
         $.ajax({
            url: getUserURL,
            type: 'GET',
            async : false,
            success : function(resultData) {
               console.log(JSON.stringify(resultData));
               userIds = JSON.parse(JSON.stringify(resultData));
            }
          });   
         var getRolesURL = "http://192.168.1.109:1300/rolemanage/api/roles";
         $.ajax({
            url: getRolesURL,
            type: 'GET',
            async : false,
            success : function(resultData) {
               console.log(JSON.stringify(resultData));
               roles = JSON.parse(JSON.stringify(resultData));
            },
            error : function(data) {
               var blah = JSON.stringify(data);
               console.log(JSON.stringify(data));
            }
          });   
            
          var myTable = "<table class=\"table table-condensed\">";
          myTable += "<thead>";
          myTable += "<tr>";
          myTable += "<th>UserId</th>";
          myTable += "<th>Current Role</th>";
          myTable += "<th>Set Role</th>";
          myTable += "</thead>";
          myTable += "<tBody>";
          for(i=0; i<userIds.length; i++) {
             myTable += "<tr>";
             myTable += "<td id=userId" + i +">" + userIds[i].username + "</td>";
             myTable += "<td>" + userIds[i].role + "</td>";
             myTable += "<td>";
             myTable += "<select class=\"selectpicker\" id=roleSetter" +i+">";


             for(j=0; j<roles.length; j++) {
                myTable += "<option>" + roles[j].role_name  + "</option>";
             }


             myTable += "</select></td>";
             myTable += "<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"update("+i+", " +userIds[i].role+")\">update</button</td>";       
             myTable += "<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"confirmDel("+i+", " +userIds[i].role+")\">delete</button</td>"; 
             myTable += "</tr>";
          }   
          myTable += "</tBody>";
          myTable += "</table>";
          
          console.log(myTable);
          document.write(myTable);      
      }   

   </script>
</head>
   
<body>
   <div class="container">
      <h2>Administrator Page</h2>
      <p></p>  
      <script>
         userTable();
      </script>
   </div>
</body>
   
</html>
