package org.MdmSystemTools.Application.model.DTO

import androidx.compose.ui.graphics.Color
import org.MdmSystemTools.Application.utils.AppConstants

data class GrupoDto(
    val id: String,
    val nome: String,
    val cor: Color,
    val descricao: String = ""
)

// Grupos predefinidos com suas cores centralizadas
object GruposPredefinidos {
    val grupos = listOf(
        GrupoDto(
            id = "desenvolvimento",
            nome = "Desenvolvimento",
            cor = AppConstants.AppColors.groupDevelopment,
            descricao = "Equipe de desenvolvimento de software"
        ),
        GrupoDto(
            id = "design",
            nome = "Design",
            cor = AppConstants.AppColors.groupDesign,
            descricao = "Equipe de design e UX/UI"
        ),
        GrupoDto(
            id = "marketing",
            nome = "Marketing",
            cor = AppConstants.AppColors.groupMarketing,
            descricao = "Equipe de marketing e vendas"
        ),
        GrupoDto(
            id = "vendas",
            nome = "Vendas",
            cor = AppConstants.AppColors.groupSales,
            descricao = "Equipe de vendas e negócios"
        ),
        GrupoDto(
            id = "suporte",
            nome = "Suporte",
            cor = AppConstants.AppColors.groupSupport,
            descricao = "Equipe de suporte técnico"
        ),
        GrupoDto(
            id = "recursos_humanos",
            nome = "Recursos Humanos",
            cor = AppConstants.AppColors.groupHR,
            descricao = "Equipe de recursos humanos"
        ),
        GrupoDto(
            id = "financeiro",
            nome = "Financeiro",
            cor = AppConstants.AppColors.groupFinance,
            descricao = "Equipe financeira"
        ),
        GrupoDto(
            id = "operacoes",
            nome = "Operações",
            cor = AppConstants.AppColors.groupOperations,
            descricao = "Equipe de operações"
        )
    )
}