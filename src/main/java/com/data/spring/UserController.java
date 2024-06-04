package com.data.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserRepository ur;
	
	@Autowired
	VehicleRepository vr;
	
	
	@GetMapping
	public Page<User> getAll(@PageableDefault (sort="name" , direction=Sort.Direction.ASC) Pageable pageable){
		
		return ur.findAll(pageable);
		
		
	}
	
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable int id )
	{
		return ur.findById(id).orElseGet(null);
	}
 	
	
	@PostMapping
	public User saveUser(@RequestBody User user)
	{
		User u = new User();
		u.setName(user.getName());
		
		List<Vehicle> list = new ArrayList<>();
		
		for(Vehicle v : user.getVehicle())
		{
			Vehicle ve = new Vehicle();
			ve.setModel(v.getModel());
			ve.setYear(v.getYear());
			
			ve.setUser(u);
			
			list.add(ve);
			
		}
		
		u.setVehicle(list);
		
		return ur.save(u);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteuser(@PathVariable int id){
		ur.deleteById(id);
	}
	
	
	
}
