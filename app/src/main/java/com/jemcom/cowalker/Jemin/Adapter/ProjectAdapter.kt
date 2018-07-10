package com.jemcom.cowalker.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Item.ProjectItem
import com.jemcom.cowalker.R

class ProjectAdapter(private var projectItems : ArrayList<ProjectItem>, var requestManager : RequestManager) : RecyclerView.Adapter<ProjectViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ProjectViewHolder(mainView)
    }

    override fun getItemCount(): Int = projectItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        requestManager.load(projectItems[position].img_url).into(holder.projectImage)
        holder.projectTitle.text = projectItems[position].title
        holder.projectArea.text = projectItems[position].area
        holder.projectDepartment.text = projectItems[position].department
        holder.projectAim.text = projectItems[position].aim
    }
}