<!DOCTYPE html>
<html>

<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
</head>

<body>

<h2>${message}</h2>


<table style="width:100%">
<col width=30%>
 <col width=30%>
<col width=20%>
 <col width=20%>
 
<tr>
    <th>Id</th>
    <th>Message</th>
    <th>Date</th>
    <th>Author</th>
    <th>Tag</th>
    
</tr>
<#list commits as commit>
	 <tr> <td> ${commit_index + 1}. ${commit.id} </td>  
	      <td>  ${commit.message} </td>
	      <td>  ${commit.date} </td>
	      <td>  ${commit.author} </td>  
	      <td>  ${commit.tag} </td>  
	      
	  </tr>
</#list>
</table>



</body>
</html>





