package x40240.shaun.adriano.a2.app.model;

import java.io.Serializable;

public final class PersonInfo
    implements Serializable
{
    private static final long serialVersionUID = 3202578332355446282L;
    
    public static final int GENDER_UNKNOWN   = 0;
    public static final int GENDER_MALE      = 1;
    public static final int GENDER_FEMALE    = 2;
    
    private int gender;
    private String firstname;
    private String lastname;
    private String favmeal = "Nothing";

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname () {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    /**
	 * @return the favmeal
	 */
	public String getFavmeal() {
		return favmeal;
	}

	/**
	 * @param favmeal 
	 * 				the favmeal to set
	 */
	public void setFavmeal(String favmeal) {
		this.favmeal = favmeal;
	}

	/**
     * @return the gender
     */
    public int getGender () {
        return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender (int gender) {
        this.gender = gender;
    }
}
