package com.patrykkosieradzki.todo.backend.validator.password;


import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

   @Override
   public boolean isValid(String password, ConstraintValidatorContext context) {
      if (password == null) {
         context.buildConstraintViolationWithTemplate("Password can't be null").addConstraintViolation();
         return false;
      }

      PasswordValidator validator = new PasswordValidator(List.of(
              new LengthRule(8, 30),
              new LowercaseCharacterRule(1),
              new UppercaseCharacterRule(1),
              new DigitCharacterRule(1),
              new SpecialCharacterRule(1),
              new WhitespaceRule()
      ));

      RuleResult result = validator.validate(new PasswordData(password));
      return result.isValid();
   }

}
