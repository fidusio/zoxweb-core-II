package org.zoxweb.client.controller;

import org.zoxweb.shared.data.events.ValueSelectionListener;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;

public class CustomAnchorController<V>
    implements ClickHandler
{
	
	private Anchor anchor;
	private V value;
	private ValueSelectionListener<V> valueSelectionListener;
	private HandlerRegistration clickHandlerRegistration;
	
	public CustomAnchorController(ValueSelectionListener<V> valueSelectionListener, String text, V value)
    {
		this();
		addValueSelectionListener(valueSelectionListener);
		setAnchorText(text);
		setValue(value);
	}
	
	public CustomAnchorController()
    {
		 anchor = new Anchor();
		 anchor.getElement().getStyle().setCursor(Cursor.POINTER);
		 
		 clickHandlerRegistration = anchor.addClickHandler(this);
	}
	
	public Anchor getAnchor()
    {
		return anchor;
	}
	
	public void setAnchorText(String text)
    {
		anchor.setText(text);
	}

	public V getValue()
    {
		return value;
	}
	
	public void setValue(V value)
    {
		this.value = value;
	}

	public void addValueSelectionListener(ValueSelectionListener<V> valueSelectionListener)
    {
		this.valueSelectionListener = valueSelectionListener;
	}
	
	public void destroy()
    {
		if (clickHandlerRegistration != null)
		{
			clickHandlerRegistration.removeHandler();
		}
	}
	
	@Override
	public void onClick(ClickEvent event)
    {
		if (valueSelectionListener != null)
		{
			valueSelectionListener.selectedValue(getValue());
		}
	}

}