package local.iot.devices.commons.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import local.iot.devices.commons.interfaces.Printable;

public class CurrencyRate implements Printable
{
	private String ccy;
	private String baseCcy;
	private String buy;
	private String sale;

	public CurrencyRate()
	{
	}

	public CurrencyRate(String ccy, String baseCcy, String buy, String sale)
	{
		this.ccy = ccy;
		this.baseCcy = baseCcy;
		this.buy = buy;
		this.sale = sale;
	}

	@Override
	public String stringify()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ccy:").append(ccy).append(",");
		sb.append("baseCcy:").append(baseCcy).append(",");
		sb.append("buy:").append(buy).append(",");
		sb.append("sale:").append(sale);
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return stringify();
	}

	public String getCcy()
	{
		return ccy;
	}

	public void setCcy(String ccy)
	{
		this.ccy = ccy;
	}

	@JsonGetter(value = "base_ccy")
	public String getBaseCcy()
	{
		return baseCcy;
	}

	@JsonSetter(value = "base_ccy")
	public void setBaseCcy(String baseCcy)
	{
		this.baseCcy = baseCcy;
	}

	public String getBuy()
	{
		return buy;
	}

	public void setBuy(String buy)
	{
		this.buy = buy;
	}

	public String getSale()
	{
		return sale;
	}

	public void setSale(String sale)
	{
		this.sale = sale;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseCcy == null) ? 0 : baseCcy.hashCode());
		result = prime * result + ((buy == null) ? 0 : buy.hashCode());
		result = prime * result + ((ccy == null) ? 0 : ccy.hashCode());
		result = prime * result + ((sale == null) ? 0 : sale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyRate other = (CurrencyRate) obj;
		if (baseCcy == null)
		{
			if (other.baseCcy != null)
				return false;
		} else if (!baseCcy.equals(other.baseCcy))
			return false;
		if (buy == null)
		{
			if (other.buy != null)
				return false;
		} else if (!buy.equals(other.buy))
			return false;
		if (ccy == null)
		{
			if (other.ccy != null)
				return false;
		} else if (!ccy.equals(other.ccy))
			return false;
		if (sale == null)
		{
			if (other.sale != null)
				return false;
		} else if (!sale.equals(other.sale))
			return false;
		return true;
	}
}
