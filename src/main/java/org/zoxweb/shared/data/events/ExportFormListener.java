package org.zoxweb.shared.data.events;

import org.zoxweb.shared.data.events.ActionBaseListener;

public interface ExportFormListener<V>
	extends ActionBaseListener<V>
{

	public void actionExportForm(V v);
	
}
