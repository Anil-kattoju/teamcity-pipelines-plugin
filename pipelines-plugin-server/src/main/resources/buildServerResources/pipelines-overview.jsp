<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="/WEB-INF/functions/util" %>
<jsp:useBean id="pipelines" type="java.util.List<org.jetbrains.teamcity.pipelines.PipelineBean>" scope="request"/>
<jsp:useBean id="activePipelineId" type="java.lang.String" scope="request"/>
<bs:linkCSS>
    /css/project.css
</bs:linkCSS>
<table>
    <tr>
        <td class="pipelines-sidebar">
            <div class="category">
                Configured pipelines
            </div>
            <div id="pipeLinesTabsContainer" class="simpleTabs clearfix"></div>
        </td>
        <td>
            <div id="pipeline-history"></div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    (function() {
        var tabs = new TabbedPane("piplines-tabs-pane");
        <c:forEach var="pipeline" items="${pipelines}">
        tabs.addTab('<c:out value="${pipeline.id}"/>', {
            caption: '<c:out value="${pipeline.name}"/> (<c:out value="${pipeline.count}"/>)',
            url: '#'
        });
        </c:forEach>
        tabs.showIn('pipeLinesTabsContainer');
        tabs.setActiveCaption('${activePipelineId}');
        // Example: "Users and Groups -- TeamCity" -> "Users and Groups > Users -- TeamCity"
       // var tabTitle = '> <c:out value="${extensionTab.tabTitle}"/> -- TeamCity';
        var tabTitle = '> Other pipline -- TeamCity';

        if (document.title.indexOf(tabTitle) == -1) {
            document.title = document.title.replace(/-- TeamCity$/, tabTitle);
        }
    })();
</script>