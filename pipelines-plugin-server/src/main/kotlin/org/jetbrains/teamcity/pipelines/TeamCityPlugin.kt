package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.web.openapi.project.ProjectTab
import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.web.openapi.PluginDescriptor
import javax.servlet.http.HttpServletRequest
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.users.SUser

/**
 * Created by Nikita.Skvortsov
 * date: 07.04.2014.
 */

open class ProjectPipelinesTab(pagePlaces: PagePlaces,
                               projectManager: ProjectManager,
                               descriptor: PluginDescriptor) : ProjectTab("piplines-overview", "Pipelines",
        pagePlaces, projectManager, descriptor.getPluginResourcesPath("pipelines-overview.jsp")) {

    {
        addCssFile(descriptor.getPluginResourcesPath("css/pipelines-view.css"))
        addJsFile(descriptor.getPluginResourcesPath("css/pipelines-view.js"))
    }

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest, project: SProject, user: SUser?) {

        val pipelines = listOf(
                PipelineBean("id_1", "Deployment", 1),
                PipelineBean("id_1", "Old deployment", 45),
                PipelineBean("id_1", "Release pipeline", 16)
        )

        model["pipelines"] = pipelines
        model["activePipelineId"] = pipelines[0].id
    }
}


class PipelineBean(val id: String, val name: String, val count: Int)