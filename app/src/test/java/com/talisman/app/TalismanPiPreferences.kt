package com.talisman.app

import com.example.tarun.kotlin.Preferences

/**
 * Created by varun on 11/8/17.
 */
class TalismanPiPreferences : Preferences()
{
    var loginDone by booleanPref(Constants.LOGIN_DONE)
    var actualNumber by stringPref(Constants.ACTUAL_NUMBER)
    var referenceNo by stringPref(Constants.REFERENCE_NO)
    var agentNo by stringPref(Constants.AGENT_NO)
    var businessid by stringPref(Constants.BUSINESS_ID)
}