package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.web.openapi.WebControllerManager
import jetbrains.buildServer.controllers.admin.projects.EditProjectTab
import javax.servlet.http.HttpServletRequest
import jetbrains.buildServer.controllers.FormUtil
import jetbrains.buildServer.controllers.BaseFormXmlController
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView
import org.jdom.Element
import jetbrains.buildServer.web.openapi.ControllerAction
import jetbrains.buildServer.serverSide.SProject
import java.util.ArrayList

/**
 * Created by Nikita.Skvortsov
 * date: 09.04.2014.
 */

open class ProjectPipelinesAdminTab(pagePlaces: PagePlaces,
                                    descriptor: PluginDescriptor,
                                    server: SBuildServer,
                                    webControllerManager: WebControllerManager): EditProjectTab(pagePlaces, "piplines-admin",
        descriptor.getPluginResourcesPath("pipelines-admin.jsp"), "Pipelines settings") {

    val adminController: ProjectPipelinesAdminController

    {
        adminController = ProjectPipelinesAdminController(server, webControllerManager)
        addCssFile(descriptor.getPluginResourcesPath("css/pipelines.css"))
        addJsFile(descriptor.getPluginResourcesPath("js/pipelines-admin.js"))
    }


    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest) {
        super<EditProjectTab>.fillModel(model, request)

        val pipelinesSettingsForm = getForm(request)
        if (pipelinesSettingsForm != null) {
            model["pipelines"] = PipelinesAdminBean(pipelinesSettingsForm)
        }
    }

    fun getForm(request: HttpServletRequest): PipelinesSettingsForm? {
        val project = getProject(request)
        if (project == null ) {
            return null
        }
        return FormUtil.getOrCreateForm(request, javaClass<PipelinesSettingsForm>(), project.getExternalId() + "_piple", { PipelinesSettingsForm(project) })
    }

    inner class ProjectPipelinesAdminController(server: SBuildServer,
                                                private val controllerManager: WebControllerManager): BaseFormXmlController(server) {
        {
            controllerManager.registerController("/pipelineAdminCtrl", this)
            controllerManager.registerAction(this, AddPipelineAction())
        }

        override fun doGet(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
            throw UnsupportedOperationException()
        }

        override fun doPost(request: HttpServletRequest, response: HttpServletResponse, xmlResponse: Element) {
            val action = controllerManager.getAction(this, request)
            if (action != null) {
                action.process(request, response, xmlResponse)
            }
        }
    }

    inner class AddPipelineAction(): ControllerAction {
        override fun canProcess(request: HttpServletRequest): Boolean {
            return request.getAttribute("newPipelineName") != null
        }
        override fun process(request: HttpServletRequest, response: HttpServletResponse, xmlResponse: Element?) {
            val newName = request.getAttribute("newPipelineName") as String
            val pipelinesSettingsForm = getForm(request)
            pipelinesSettingsForm?.addPipeline(Pipeline("id_${newName}", newName))
        }
    }

}