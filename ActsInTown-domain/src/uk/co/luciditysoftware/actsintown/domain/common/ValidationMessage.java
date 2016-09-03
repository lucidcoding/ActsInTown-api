package uk.co.luciditysoftware.actsintown.domain.common;

public class ValidationMessage 
{
	private ValidationMessageType type;
	private String field;
	private String text;
	
	public ValidationMessageType getType() 
	{
		return type;
	}
	
	public void setType(ValidationMessageType type) 
	{
		this.type = type;
	}
	
	public String getField() 
	{
		return field;
	}
	
	public void setField(String field) 
	{
		this.field = field;
	}
	
	public String getText() 
	{
		return text;
	}
	
	public void setText(String text) 
	{
		this.text = text;
	}
}

