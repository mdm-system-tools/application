package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.dto.GroupDto
import org.MdmSystemTools.Application.view.theme.AppConstants

class GroupRepositoryImpl : GroupRepository {
	override fun getListGroups(): List<GroupDto> {
		val grupos = listOf(
			GroupDto(
				id = "desenvolvimento",
				name = "Desenvolvimento",
				color = AppConstants.AppColors.groupDevelopment,
				description = "Equipe de desenvolvimento de software"
			),
			GroupDto(
				id = "design",
				name = "Design",
				color = AppConstants.AppColors.groupDesign,
				description = "Equipe de design e UX/UI"
			),
			GroupDto(
				id = "marketing",
				name = "Marketing",
				color = AppConstants.AppColors.groupMarketing,
				description = "Equipe de marketing e vendas"
			),
			GroupDto(
				id = "vendas",
				name = "Vendas",
				color = AppConstants.AppColors.groupSales,
				description = "Equipe de vendas e negócios"
			),
			GroupDto(
				id = "suporte",
				name = "Suporte",
				color = AppConstants.AppColors.groupSupport,
				description = "Equipe de suporte técnico"
			),
			GroupDto(
				id = "recursos_humanos",
				name = "Recursos Humanos",
				color = AppConstants.AppColors.groupHR,
				description = "Equipe de recursos humanos"
			),
			GroupDto(
				id = "financeiro",
				name = "Financeiro",
				color = AppConstants.AppColors.groupFinance,
				description = "Equipe financeira"
			),
			GroupDto(
				id = "operacoes",
				name = "Operações",
				color = AppConstants.AppColors.groupOperations,
				description = "Equipe de operações"
			)
		)
		return grupos
	}
}