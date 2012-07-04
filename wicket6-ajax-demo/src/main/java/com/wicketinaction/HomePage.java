package com.wicketinaction;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * A demo page for HandlebarsButton
 */
public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters)
	{
		super(parameters);

		Form<Article> form = new Form<Article>("form", new CompoundPropertyModel<Article>(new Article()));
		add(form);

		TextField<String> title = new TextField<String>("title");
		TextArea<String> content = new TextArea<String>("content");

		HandlebarsButton<Article> update = new HandlebarsButton<Article>("update", "article-template", ".article")
		{
			@Override
			protected CharSequence asJson(Article article)
			{
				CharSequence json = JsonSerializer.toJson(article);
				return json;
			}
		};

		form.add(title, content, update);
    }

	/**
	 * A simple POJO used as a model object for the Wicket Form
	 */
	private static class Article implements Serializable
	{
		private String title;

		private String content;
	}

	/**
	 * A simple JSON serializer for the Article POJO
	 */
	private static class JsonSerializer
	{
		private static CharSequence toJson(Article article)
		{
			Args.notNull(article, "article");

			StringBuilder json = new StringBuilder();

			json.append('{')
					.append("\"title\": \"").append(Strings.toMultilineMarkup(article.title)).append('"')
					.append(", \"content\": \"").append(Strings.toMultilineMarkup(article.content)).append('"')
					.append('}');

			return json;
		}
	}
}
