package com.usersantiago.app.persistence.repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.IJWTUtilityService;
import com.usersantiago.app.services.IcustomerDAO;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.CustomerDTO;
import com.usersantiago.app.services.models.dtos.CustomerRowMapper;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Repository
public class CustomerRepository implements IcustomerDAO {
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final CustomerRowMapper rowMapper;
  private final SimpleJdbcInsert jdbcInsert;
  private IJWTUtilityService jwtUtilityService;
  private static final String TABLE = "customer";

  public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource,
      IJWTUtilityService jwtUtilityService, CustomerRowMapper rowMapper) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE).usingGeneratedKeyColumns("customer_id");
    this.jwtUtilityService = jwtUtilityService;
    this.rowMapper = rowMapper;
  }

  public Integer saveCustomer(CustomerCreationDTO customer) {
    return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
        .addValue("tipo_document", customer.tipoDocument())
        .addValue("document", customer.document())
        .addValue("first_name", customer.firstName())
        .addValue("last_name", customer.lastName())
        .addValue("phone", customer.phone())
        .addValue("email", customer.email())
        .addValue("password", customer.password())
        .addValue("birthdate", customer.birthdate()))
            .intValue();
  }

  public List<CustomerEntity> getAllCustomers() {
    var querySqlSelectAll = "SELECT customer_id, tipo_document, document,first_name, last_name, "
        + "phone, email, password, birthdate FROM " + TABLE + " WHERE active = 1";
    return namedParameterJdbcTemplate.query(querySqlSelectAll, rowMapper);
  }

  public boolean existsCustomerWithEmail(String email) {
    String sql = "SELECT COUNT(customer_id) FROM customer WHERE email = :email";
    Map<String, Object> paramMap = Collections.singletonMap("email", email);
    Integer count = namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    return count != null && count > 0;
  }

  public Optional<CustomerEntity> selectUserByEmail(String email) {
    var sql = """
        SELECT customer_id, tipo_document, document,first_name, last_name,
        phone, email, password, birthdate FROM public.customer WHERE email = :email;
        """;
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("email", email);

    return namedParameterJdbcTemplate.query(sql, paramMap, rowMapper).stream().findFirst();
  }

  public HashMap<String, Object> signin(LoginDTO customer) throws Exception {
    try {
      HashMap<String, Object> jwt = new HashMap<>();
      Optional<CustomerEntity> usersByEmail = selectUserByEmail(customer.email());

      if (usersByEmail.isEmpty()) {
        jwt.put("error", "user not registered!");
        return jwt;
      }

      if (verifyPassword(customer.password(), usersByEmail.get().getPassword())) {
        CustomerDTO customerDTO;
        customerDTO = new CustomerDTO(usersByEmail.get().getIdCustomer(), usersByEmail.get().getTipoDocument(),
            usersByEmail.get().getDocument(), usersByEmail.get().getFirstName(),
            usersByEmail.get().getLastName(), usersByEmail.get().getPhone(), usersByEmail.get().getEmail(),
            usersByEmail.get().getBirthdate());
        jwt.put("data", customerDTO);
        jwt.put("jwt", jwtUtilityService.generateJWT(usersByEmail.get().getIdCustomer()));

      } else {
        jwt.put("error", "Authentication failed!");
      }
      return jwt;

    } catch (IllegalArgumentException e) {
      System.err.println("Error generating JWT: " + e.getMessage());
      throw new Exception("Error generating JWT", e);
    } catch (Exception e) {
      System.err.println("Unknown error: " + e.toString());
      throw new Exception("Unknown error", e);
    }
  }

  private boolean verifyPassword(String enteredPassword, String storedPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();// bean que creamos en SecurityConfig
    return encoder.matches(enteredPassword, storedPassword);
  }

  public boolean existsCustomerWithDocument(String document) {
    String sql = "SELECT COUNT(customer_id) FROM public.customer WHERE document = :document";
    Map<String, Object> paramMap = Collections.singletonMap("document", document);
    System.out.println("Executing SQL: " + sql);
    System.out.println("With parameters: " + paramMap);
    Integer count = namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    return count != null && count > 0;
  }

  @Override
  public void updateCustomer(CustomerEntity customerUpdate) {
    if (customerUpdate.getTipoDocument() != null) {
      String sql = "UPDATE public.customer SET tipo_document = :tipoDocument WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("tipoDocument", customerUpdate.getTipoDocument());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
      //System.out.println("Actualizado " + result);
    }

    if (customerUpdate.getFirstName() != null) {
      String sql = "UPDATE public.customer SET first_name = :firstName WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("firstName", customerUpdate.getFirstName());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
      //System.out.println("Actualizado " + result);
    }

    if (customerUpdate.getLastName() != null) {
      String sql = "UPDATE public.customer SET last_name = :lastName WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("lastName", customerUpdate.getLastName());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
     // System.out.println("Actualizado " + result);
    }

    if (customerUpdate.getPhone() != null) {
      String sql = "UPDATE public.customer SET phone = :phone WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("phone", customerUpdate.getPhone());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
      //System.out.println("Actualizado " + result);
    }

    if (customerUpdate.getBirthdate() != null) {
      String sql = "UPDATE public.customer SET birthdate = :birthdate WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("birthdate", customerUpdate.getBirthdate());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
      //System.out.println("Actualizado " + result);
    }

    if (customerUpdate.getBirthdate() != null) {
      String sql = "UPDATE public.customer SET birthdate = :birthdate WHERE document = :document";
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("birthdate", customerUpdate.getBirthdate());
      paramMap.put("document", customerUpdate.getDocument());
      int result = namedParameterJdbcTemplate.update(sql, paramMap);
      System.out.println("Actualizado " + result);
    }
  }

  @Override
  public Optional<CustomerEntity> selectCustomerByDocument(String document) {
    var sql = """
        SELECT customer_id, tipo_document, document, first_name, last_name, phone, email, password, birthdate FROM public.customer WHERE document = :document;
        """;
    Map<String, Object> paramMap = Collections.singletonMap("document", document);
    return namedParameterJdbcTemplate.query(sql, paramMap, rowMapper).stream().findFirst();
  }
}
