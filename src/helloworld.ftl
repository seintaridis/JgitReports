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

<ul>

<#list stats as stat>
	 <li> ${stat_index + 1}. ${stat} </li>
</#list>

</ul>  

<table style="width:100%">


<tr>
    <th>Branches</th>

  </tr>
<#list branches as branch>
	 <tr> <td> <a href= "${branch}.html"> ${branch_index + 1}. ${branch} </a> </td> </tr>
</#list>
</table>




<table style="width:50%">
<col width=30%>
 <col width=20%>

 
<tr>
    <th>name</th>
    <th>percentage</th>
</tr>
<#list authors as author>
	 <tr>  
	      <td>  ${author.name} </td>
	      <td>  ${author.commitPercentage}% </td>
	    
	  </tr>
</#list>
</table>



<table style="width:50%">
<col width=30%>
 <col width=20%>

 
<tr>
    <th>name</th>
    <th>percentage</th>
</tr>
<#list branchStats as branchStat>
	 <tr>  
	      <td>  ${branchStat.name} </td>
	      <td>  ${branchStat.commitPercentage}% </td>
	    
	  </tr>
</#list>
</table>





</body>
</html>





