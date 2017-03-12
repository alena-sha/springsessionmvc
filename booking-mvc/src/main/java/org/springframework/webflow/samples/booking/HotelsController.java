package org.springframework.webflow.samples.booking;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import conversation.Conversation;

@Controller
public class HotelsController {

    private BookingService bookingService;

    @Autowired
    public HotelsController(BookingService bookingService) {
	this.bookingService = bookingService;
    }

    @RequestMapping(value = "/hotels/search", method = RequestMethod.GET)
    public void search(SearchCriteria searchCriteria, Principal currentUser, Model model,HttpServletRequest request) {
    	request.getSession().setAttribute("conversation", UUID.randomUUID().toString());
    	System.out.println("!!!!!!!!!!!!conversation="+request.getSession().getAttribute("conversation"));
       	if (currentUser != null) {
		    List<Booking> booking = bookingService.findBookings(currentUser.getName());
		    model.addAttribute(booking);
		}
    	Conversation currentConversation=(Conversation)request.getAttribute("currentConversation");
    	String url=(String)request.getAttribute("addConversationUrl");
    	if(currentConversation!=null){
    		model.addAttribute("currentConversation",currentConversation);
    		model.addAttribute("addConversationUrl", url);
    	}
    
   }
    
    

    @RequestMapping(value = "/hotels", method = RequestMethod.GET)
    public String list(SearchCriteria criteria, Model model) {
    	
		
	List<Hotel> hotels = bookingService.findHotels(criteria);
	model.addAttribute(hotels);
	return "hotels/list";
    }

    @RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
	model.addAttribute(bookingService.findHotelById(id));
	return "hotels/show";
    }

    @RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
    public String deleteBooking(@PathVariable Long id) {
	bookingService.cancelBooking(id);
	/*HttpSession session = req.getSession(false);
	if (session != null) {
		session.invalidate();
	}*/
	return "redirect:../hotels/search";
    }

}
