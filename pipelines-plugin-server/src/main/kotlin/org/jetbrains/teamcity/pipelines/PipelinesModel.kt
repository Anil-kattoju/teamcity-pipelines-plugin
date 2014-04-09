package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.serverSide.SProject
import java.util.ArrayList

/**
 * Created by Nikita.Skvortsov
 * date: 09.04.2014.
 */


open class Pipeline(val id: String, val name: String) {
    public val steps : MutableList<String> = arrayListOf()
}

open class PipelineBean(val id: String, val name: String, val count: Int) {
    public val stepNames : MutableList<String> = arrayListOf()
}

open class PipelinesSettingsForm(project: SProject) {
    val pipelines: MutableList<Pipeline> = ArrayList<Pipeline>()
    fun addPipeline(p: Pipeline) {
        pipelines.add(p)
    }

    fun get(pipeId: String): Pipeline? {
        return pipelines.firstOrNull { it.id == pipeId }
    }
}

open class PipelinesAdminBean(form: PipelinesSettingsForm) {
    val list : List<PipelineBean>
    {
        list = form.pipelines.map {
            val bean = PipelineBean(it.id, it.name, 0)
            it.steps.forEach { bean.stepNames.add(it) }
            bean
        }
    }
}
