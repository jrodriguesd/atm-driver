/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc.model;

import org.junit.Test;	
import org.junit.Before;	

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.*;

import java.util.List;

import org.jpos.atmc.model.*;

public class IsoError2ATMTest
{
    private PojoClass atmconfigPojo;
    private Validator validator;	

	@Before
	public void setup() 
	{
	    atmconfigPojo = PojoClassFactory.getPojoClass(IsoError2ATM.class);
	}	

	@Test
	public void testPojoGetterMustExistRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Make sure we have a getter
		    .with(new GetterMustExistRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoSetterMustExistRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Make sure we have a setter
	        .with(new SetterMustExistRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoPrimitivesRule() 
	{
	    validator = ValidatorBuilder.create()
	        // We don't want any primitives in our Pojos.
	        .with(new NoPrimitivesRule())
            .build();
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoNestedClassRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Pojo's should stay simple, don't allow nested classes, anonymous or declared.
	        .with(new NoNestedClassRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoStaticExceptFinalRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Static fields must be final
	        .with(new NoStaticExceptFinalRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoSerializableMustHaveSerialVersionUIDRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Serializable must have serialVersionUID
	        .with(new SerializableMustHaveSerialVersionUIDRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoFieldShadowingRule() 
	{
	    validator = ValidatorBuilder.create()
	        // Don't shadow parent's field names.
	        .with(new NoFieldShadowingRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoPublicFieldsExceptStaticFinalRule() 
	{
	    validator = ValidatorBuilder.create()
	        // What about public fields, use one of the following rules
	        // allow them only if they are static and final.
	        .with(new NoPublicFieldsExceptStaticFinalRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoNoPublicFieldsRule() 
	{
	    // Not allow ANY public fields in a Pojo.
	    validator = ValidatorBuilder.create()
	        .with(new NoPublicFieldsRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoSetterTester() 
	{
	    // Make sure our setters are behaving as expected.
	    validator = ValidatorBuilder.create()
	        .with(new SetterTester())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoGetterTester() 
	{
	    // Make sure our getters are behaving as expected.
	    validator = ValidatorBuilder.create()
	        .with(new GetterTester())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoDefaultValuesNullTester() 
	{
	    // We don't want any default values to any fields - unless they are declared final or are primitive.
	    validator = ValidatorBuilder.create()
	        .with(new DefaultValuesNullTester())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoBusinessKeyMustExistRule() 
	{
	    // We don't want any default values to any fields - unless they are declared final or are primitive.
	    validator = ValidatorBuilder.create()
	        .with(new BusinessKeyMustExistRule())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

	@Test
	public void testPojoBusinessIdentityTester() 
	{
	    // We don't want any default values to any fields - unless they are declared final or are primitive.
	    validator = ValidatorBuilder.create()
	        .with(new BusinessIdentityTester())
            .build();	    		
	    validator.validate(atmconfigPojo);
	}

    private String topLevelPackageName = "org.jpos.atmc.model";

    @Test
    public void testPojoTestClassMustBeProperlyNamedRule() 
	{
	    validator = ValidatorBuilder.create()
	        .with(new TestClassMustBeProperlyNamedRule())
            .build();	    		

        List<PojoClass> pojoClasses = 
            PojoClassFactory.getPojoClassesRecursively(topLevelPackageName, null);
        for (PojoClass pojoClass : pojoClasses) 
		{
	        validator.validate(pojoClass);
        }
    }

}
