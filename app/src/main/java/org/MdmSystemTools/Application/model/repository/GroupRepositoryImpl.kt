package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.DTO.GroupDto
import org.MdmSystemTools.Application.view.theme.AppConstants

class GroupRepositoryImpl : GroupRepository {
	override fun getListGroups(): List<GroupDto> {
		val grupos = listOf(
			GroupDto(
				id = "desenvolvimento",
				nome = "Desenvolvimento",
				cor = AppConstants.AppColors.groupDevelopment,
				descricao = "Equipe de desenvolvimento de software"
			),
			GroupDto(
				id = "design",
				nome = "Design",
				cor = AppConstants.AppColors.groupDesign,
				descricao = "Equipe de design e UX/UI"
			),
			GroupDto(
				id = "marketing",
				nome = "Marketing",
				cor = AppConstants.AppColors.groupMarketing,
				descricao = "Equipe de marketing e vendas"
			),
			GroupDto(
				id = "vendas",
				nome = "Vendas",
				cor = AppConstants.AppColors.groupSales,
				descricao = "Equipe de vendas e negócios"
			),
			GroupDto(
				id = "suporte",
				nome = "Suporte",
				cor = AppConstants.AppColors.groupSupport,
				descricao = "Equipe de suporte técnico"
			),
			GroupDto(
				id = "recursos_humanos",
				nome = "Recursos Humanos",
				cor = AppConstants.AppColors.groupHR,
				descricao = "Equipe de recursos humanos"
			),
			GroupDto(
				id = "financeiro",
				nome = "Financeiro",
				cor = AppConstants.AppColors.groupFinance,
				descricao = "Equipe financeira"
			),
			GroupDto(
				id = "operacoes",
				nome = "Operações",
				cor = AppConstants.AppColors.groupOperations,
				descricao = "Equipe de operações"
			)
		)
		return grupos
	}
}