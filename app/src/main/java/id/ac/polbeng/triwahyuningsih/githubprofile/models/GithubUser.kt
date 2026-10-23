
package id.ac.polbeng.triwahyuningsih.githubprofile.models

import com.google.gson.annotations.SerializedName

data class GithubUser(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("email")
	val email: String?,

	@field:SerializedName("bio")
	val bio: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("created_at")
	val createdAt: String
)
