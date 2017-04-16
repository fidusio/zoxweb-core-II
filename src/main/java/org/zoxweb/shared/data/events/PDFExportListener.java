package org.zoxweb.shared.data.events;

/**
 * The PDF export listener interface.
 * @param <V>
 */
public interface PDFExportListener<V>
	extends ActionBaseListener<V>
{

    /**
     * Executes export PDF action for specified input.
     * @param v
     */
	public void actionExportPDF(V v);

}