package com.fordays.fdpay.upload;
public class Attachment

extends org.apache.struts.action.ActionForm implements Cloneable
{
	private static final long serialVersionUID = 1L;



	protected int id;
	protected String name;
	protected String vname;
	protected Integer size;
	protected String xname;
	protected String contentType;
	private Integer type;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getVname()
	{
		return vname;
	}
	public void setVname(String vname)
	{
		this.vname = vname;
	}
	public Integer getSize()
	{
		return size;
	}
	public int getSizeOnKB()
	{
		int s=size.intValue()/1024;
		if(s<1)
			s=1;
		return s;
	}
	
	public void setSize(Integer size)
	{
		this.size = size;
	}
	public String getXname()
	{
		return xname;
	}
	public void setXname(String xname)
	{
		this.xname = xname;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
