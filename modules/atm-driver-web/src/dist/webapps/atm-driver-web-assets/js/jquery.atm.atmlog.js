/**
 *
 *
 */
class ATMLog
{
    constructor() 
	{
	    this.atmLog = [];
		this.jwt = "";
		this.baseURL = "/jpos/api";
		this.id = 1;
	}

	setJWT(jwt)
	{
		this.jwt = jwt;
	}

	setBaseURL(baseURL)
	{
		this.baseURL = baseURL;
	}

	setId(id)
	{
		this.id = id;
	}

    loadAtmLog() 
	{
		let atmLog = this;  // para poder usarlo en otro contexto (Toda La Funcion)
		let urlStr = this.baseURL + "/atmlogs/" + this.id;
	    // console.log("jquery.atm.atmlog.js 34 loadAtmLog urlStr >" + urlStr + "<");
        $.ajax(
        {
            url: atmLog.baseURL + "/atmlogs/" + atmLog.id,
            method: "GET",
            headers: {
              Authorization: 'Bearer ' + this.jwt
            },
            success: function(data)
	        {
	            console.log("jquery.atm.atmlog.js 44");
                atmLog.atmLog = JSON.parse( JSON.stringify(data) );
                $.each(atmLog.atmLog, function(id, value)
	            {
					// console.log("$(#" + id + ").val() >" + $("#"+ id).val() + "<");
					// console.log("id >" + id + "< value >" + value + "<");
					$("#"+ id).val(value);

                    // No esta Completo falta el else para activar el tab ???
					if ( (id == "atmlog_atm_request")              && (value == null) ) $("#nav-0-tab").hide();
					if ( (id == "atmlog_iso_request")              && (value == null) ) $("#nav-1-tab").hide();
					if ( (id == "atmlog_iso_reply")                && (value == null) ) $("#nav-2-tab").hide();
					if ( (id == "atmlog_atm_reply")                && (value == null) ) $("#nav-3-tab").hide();
					if ( (id == "atmlog_atm_confirmation")         && (value == null) ) $("#nav-4-tab").hide();
					if ( (id == "atmlog_iso_confirmation_request") && (value == null) ) $("#nav-5-tab").hide();
					if ( (id == "atmlog_iso_confirmation_reply")   && (value == null) ) $("#nav-6-tab").hide();
					if ( (id == "atmlog_op_code")                  && (value == null) ) $("#TransactionData").hide();
                });
            },
            error: function(xhr, resp, text) 
	    	{
	            console.log("jquery.atm.atmlog.js 53");
			}
        });
	}

    init() 
	{
		this.loadAtmLog();
	}
}