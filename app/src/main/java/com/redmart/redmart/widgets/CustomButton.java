package com.redmart.redmart.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.redmart.redmart.R;

import java.util.HashMap;

public class CustomButton extends AppCompatButton {

	private final static HashMap<String, Typeface> typefaceDictionary = new HashMap<>();
	private static Typeface typeface;

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTypeface);
		String fontName = styledAttrs.getString(R.styleable.CustomFontTypeface_typeface);
		styledAttrs.recycle();
		if (fontName != null) {
			this.setTypeface(GetTypeFace(context.getAssets(), fontName), typeface.getStyle());
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			// only for LOLLIPOP and newer versions
			setStateListAnimator(null);
		}
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