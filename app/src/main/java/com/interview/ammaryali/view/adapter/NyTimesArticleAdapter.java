package com.interview.ammaryali.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.interview.ammaryali.R;
import com.interview.ammaryali.model.Article;
import com.interview.ammaryali.view.article.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NyTimesArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static List<Article> articles;
    private final int TEXTONLY = 0, THUMBNAIL = 1;
    private Context mContext;

    public NyTimesArticleAdapter(Context context, ArrayList<Article> articles) {
        NyTimesArticleAdapter.articles = articles;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Article article = articles.get(position);
        if (TextUtils.isEmpty(article.getArticleThumbnailUrl())) {
            return TEXTONLY;
        }
        return THUMBNAIL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case TEXTONLY:
                View v1 = inflater.inflate(R.layout.item_template_text_only,
                        viewGroup, false);
                viewHolder = new TextArticleViewHolder(v1);
                break;
            case THUMBNAIL:
                View v2 = inflater.inflate(R.layout.item_template,
                        viewGroup, false);
                viewHolder = new ThumbnailArticleViewHolder(v2);
                break;
            default:
                v2 = inflater.inflate(R.layout.item_template,
                        viewGroup, false);
                viewHolder = new ThumbnailArticleViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TEXTONLY:
                TextArticleViewHolder vh1 = (TextArticleViewHolder) viewHolder;
                configureTextArticleViewHolder(vh1, position);
                break;
            case THUMBNAIL:
                ThumbnailArticleViewHolder vh2 = (ThumbnailArticleViewHolder) viewHolder;
                configureThumbnailArticleViewHolder(vh2, position);
                break;
            default:
                ThumbnailArticleViewHolder vh = (ThumbnailArticleViewHolder) viewHolder;
                configureThumbnailArticleViewHolder(vh, position);
                break;
        }
    }

    private void configureTextArticleViewHolder(TextArticleViewHolder viewHolder, int position) {
        Article article = articles.get(position);
        viewHolder.tvArticleText.setText(article.getHeadline().getMain());
    }

    private void configureThumbnailArticleViewHolder(ThumbnailArticleViewHolder viewHolder,
                                                     int position) {
        Article article = articles.get(position);

        viewHolder.ivArticle.setImageResource(0);
        if (!TextUtils.isEmpty(article.getArticleThumbnailUrl())) {
            Glide.with(mContext).load(article.getArticleThumbnailUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHolder.ivArticle);
        }
        viewHolder.tvArticle.setText(article.getHeadline().getMain());
    }

    class TextArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvArticleText)
        TextView tvArticleText;

        TextArticleViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Article article = articles.get(position);
            Toast.makeText(mContext, "Loading article...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(mContext, ArticleActivity.class);
            i.putExtra("webUrl", article.webUrl);
            mContext.startActivity(i);
        }
    }

    class ThumbnailArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivArticle)
        ImageView ivArticle;
        @BindView(R.id.tvArticle)
        TextView tvArticle;

        ThumbnailArticleViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            Article article = articles.get(getAdapterPosition());
            Toast.makeText(view.getContext(), "Loading article...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(mContext, ArticleActivity.class);
            i.putExtra("webUrl", article.webUrl);
            mContext.startActivity(i);
        }
    }
}