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

General Stats

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


Author Stats

<table style="width:100%">
<col width=10%>
 <col width=10%>
 <col width=10%>
  <col width=10%>
   <col width=10%>
 <col width=20%>
  <col width=20%>
 <col width=10%>
<tr>
    <th>name</th>
    <th>percentage</th>
    <th>commits/day</th>
    <th>commits/month</th>
    <th>commits/year</th>
    <th>averageModifiedLines</th>
    <th>averageDeletedLines</th>
    <th>averageInsertedLines</th>
    
</tr>
<#list authors as author>
	 <tr>  
	      <td>  ${author.name} </td>
	      <td>  ${author.commitPercentage}% </td>
	      <td>  ${author.commitsPerDay} </td>
	      <td>  ${author.commitsPerMonth} </td>
	      <td>  ${author.commitsPerYear} </td>
	      <td>  ${author.modifiedLinesAverage} </td>
	      <td>  ${author.deletedLinesAverage} </td>
	      <td>  ${author.insertedLinesAverage} </td>
	      	      
	      
	  </tr>
</#list>
</table>

Average commits per branch

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






<table style="width:50%">
<col width=30%>
 <col width=20%>

 
<tr>
    <th>Commits per branch per user</th>
</tr>
<#list branchAuhors as branchAuhor>
	 <tr>  
	      <td>  ${branchAuhor.name} 
	      
	 <ul>

<#list branchAuhor.listOfAthors as auth>
	 <li> ${auth.name}. ${auth.commitPercentage} </li>
</#list>

</ul>  
	      
	      </td>
	      
	    
	  </tr>
</#list>
</table>





</body>
</html>





