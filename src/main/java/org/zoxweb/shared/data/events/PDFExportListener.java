package org.zoxweb.shared.data.events;

public interface PDFExportListener<V>
	extends ActionBaseListener<V>
{

	public void actionExportPDF(V v);
}
