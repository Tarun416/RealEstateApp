package com.talisman.app

import com.example.tarun.kotlin.Preferences

/**
 * Created by varun on 11/8/17.
 */
class TalismanPiPreferences : Preferences()
{
    var loginDone by booleanPref(Constants.LOGIN_DONE)
    var agentNo by stringPref(Constants.ACTUAL_NUMBER)
    var referenceNo by stringPref(Constants.REFERENCE_NO)
    var crmbusinessid by stringPref(Constants.CRM_BUSINESS_ID)
    var status by stringPref(Constants.STATUS)
    var businessId by stringPref(Constants.BUSINESS_ID)
    var userId by stringPref(Constants.USER_ID)
    var registrationToken by stringPref(Constants.REGISTRATION_TOKEN)
    var agentName by stringPref(Constants.AGENT_NAME)
    var userName by stringPref(Constants.USER_NAME)
    var virtualNo by stringPref(Constants.VIRTUAL_NUMBER)
}