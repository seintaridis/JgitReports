<!DOCTYPE html>
<html>
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

</body>
</html>





