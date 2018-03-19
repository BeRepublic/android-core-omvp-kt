package com.omvp.commons

import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView


/**
 * Created by Ángel Gómez on 28/05/2017.
 */

object HtmlHelper {

    @JvmOverloads
    fun setText(textView: TextView, htmlContent: String, listener: OnHyperlinkClickListener? = null) {
        var content = htmlContent
        if (!content.isEmpty()) {
            val sequence = fromHtml(content)
            val strBuilder = SpannableStringBuilder(sequence)
            val urls = strBuilder.getSpans<URLSpan>(0, sequence.length, URLSpan::class.java)
            if (urls != null && urls.isNotEmpty()) {
                for (span in urls) {
                    makeLinkClickable(strBuilder, span, listener)
                }
            } else {
                var hasLinks = false
                for (word in content.split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
                    if (Patterns.WEB_URL.matcher(word).matches()) {
                        hasLinks = true
                        content = content.replace(word, "<a href='$word'>$word</a>")
                    }
                }
                if (hasLinks) {
                    setText(textView, content, listener)
                    return
                }
            }
            textView.text = strBuilder
            textView.movementMethod = LinkMovementMethod.getInstance()
        } else {
            textView.text = ""
        }
    }

    private fun fromHtml(html: String): CharSequence {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan, listener: OnHyperlinkClickListener?) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
                val uri = Uri.parse(span.url)
                listener?.onHyperlinkClick(uri)
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

}
