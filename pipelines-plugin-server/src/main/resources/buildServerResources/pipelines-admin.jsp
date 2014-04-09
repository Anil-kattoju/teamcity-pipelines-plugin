<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="util" uri="/WEB-INF/functions/util" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<bs:linkCSS>
    /css/project.css
</bs:linkCSS>

<%--@elvariable id="pipelines" type="org.jetbrains.teamcity.pipelines.PipelinesAdminBean"--%>

<forms:addButton onclick="BS.AddPipelineDialog.show(); return false">Add pipeline</forms:addButton>

<%-- pipelines list --%>
<bs:refreshable containerId="pipelinesTableContainer" pageUrl="${pageUrl}">
    <c:forEach var="pipeline" items="${pipelines.list}">
        <table>
            <tr>
                <th><c:out value="${pipeline.name}"/></th>
                <c:forEach var="stepName" items="${pipeline.stepNames}">
                    <th><c:out value="${stepName}"/></th>
                </c:forEach>
                <th><forms:addButton onclick="BS.EditStepDialog.show('${pipeline.id}'); return false">Add Step</forms:addButton></th>
            </tr>
            <tr>
                <td>
                   Steps: <c:out value="${fn:length(pipeline.stepNames)}"/>
                </td>
            </tr>
        </table>
    </c:forEach>
</bs:refreshable>


<%-- add pipeline dialog--%>
<bs:modalDialog formId="addPipelineForm" title="Add pipeline" action="/pipelineAdminCtrl.html?newPipeline"
                closeCommand="BS.AddPipelineDialog.close()"
                saveCommand="BS.AddPipelineDialog.save()">
    <table>
        <tr>
            <td>
                Pipeline name:
            </td>
            <td>
                <forms:textField maxlength="100" name="newName"/>
                <span class="error" id="error_pipelineName"></span>
            </td>
        </tr>
    </table>
    <div class="popupSaveButtonsBlock">
        <forms:submit label="Add"/>
        <forms:cancel onclick="BS.AddPipelineDialog.close();" showdiscardchangesmessage="false"/>
        <%-- <forms:saving id="saveArtifactDependencyProgress"/> --%>
    </div>
    <input type="hidden" name="projectExtId" value="${currentProject.externalId}">
</bs:modalDialog>

<%-- add step dialog --%>
<bs:modalDialog formId="editStepForm" title="Add new step" action="/pipelineAdminCtrl.html?editStep"
                closeCommand="BS.EditStepDialog.close()" saveCommand="BS.EditStepDialog.save()">
    <table>
        <tr>
            <td>
                Step name:
            </td>
            <td>
                <forms:textField maxlength="100" name="stepName"/>
                <span class="error" id="error_stepName"></span>
            </td>
        </tr>
    </table>
    <div class="popupSaveButtonsBlock">
        <forms:submit label="Add"/>
        <forms:cancel onclick="BS.EditStepDialog.close();" showdiscardchangesmessage="false"/>
            <%-- <forms:saving id="saveArtifactDependencyProgress"/> --%>
    </div>
    <input type="hidden" name="pipeId"/>
    <input type="hidden" name="projectExtId" value="${currentProject.externalId}"/>
</bs:modalDialog>
