package com.redmart.redmart.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.redmart.redmart.R;

import java.util.HashMap;

public class CustomTextView extends AppCompatTextView {

	private final static HashMap<String, Typeface> typefaceDictionary = new HashMap<>();
	private static Typeface typeface;

	public CustomTextView(Context context) {
		super(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTypeface);
		String fontName = styledAttrs.getString(R.styleable.CustomFontTypeface_typeface);
		styledAttrs.recycle();
		if (fontName != null) {
			this.setTypeface(GetTypeFace(context.getAssets(), fontName), typeface.getStyle());
		}
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private static Typeface GetTypeFace(AssetManager assertManager, String fontName) {
		if (typefaceDictionary.containsKey(fontName)) {
			typeface = typefaceDictionary.get(fontName);
		} else {
			typeface = Typeface.createFromAsset(assertManager, fontName);
			typefaceDictionary.put(fontName, typeface);
		}
		return typeface;
	}
}