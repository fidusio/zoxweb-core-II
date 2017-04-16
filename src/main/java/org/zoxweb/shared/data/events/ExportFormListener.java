package org.zoxweb.shared.data.events;

/**
 * The export form listener interface.
 * @param <V>
 */
public interface ExportFormListener<V>
	extends ActionBaseListener<V>
{

    /**
     * Executes export form action for specified input.
     * @param v
     */
	public void actionExportForm(V v);
	
}
