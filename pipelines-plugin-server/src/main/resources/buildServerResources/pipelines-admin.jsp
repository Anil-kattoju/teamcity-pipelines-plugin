<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="util" uri="/WEB-INF/functions/util" %>
<bs:linkCSS>
    /css/project.css
</bs:linkCSS>

<h2>Hello World</h2>

<forms:addButton onclick="BS.AddPipelineDialog.show(); return false">Add pipeline</forms:addButton>

<%-- pipelines list --%>
<bs:refreshable containerId="pipelinesTableContainer" pageUrl="${pageUrl}">
    <c:forEach var="pipeline" items="${pipelines.list}">
        <h3><c:out value="${pipeline.name}"/></h3>
    </c:forEach>
</bs:refreshable>


<%-- add pipeline dialog--%>
<bs:modalDialog formId="addPipelineForm" title="Add pipeline" action="/pipelineAdminCtrl"
                closeCommand="BS.AddPipelineDialog.close();"
                saveCommand="BS.AddPipelineDialog.save();">
    <table>
        <tr>
            <td>
                Pipeline name:
            </td>
            <td>
                <forms:textField maxlength="100" className="longField" name="pipelineName"/>
                <span class="error" id="error_pipelineName"></span>
            </td>
        </tr>
    </table>
</bs:modalDialog>

