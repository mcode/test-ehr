# REMS EHR FHIR Server

This sub-project hosts a HAPI FHIR server that is based on the now-deprecated [hapi-fhir-jpaserver-example](https://github.com/jamesagnew/hapi-fhir/tree/master/hapi-fhir-jpaserver-example). The new repo is found [here](https://github.com/hapifhir/hapi-fhir-jpaserver-starter).

The server this repository runs is a standard FHIR server that saves and provides FHIR resources for clients. Almost every step of the REMS workflow involves the EHR, it communicates with the [Request Generator](https://github.com/mcode/request-generator), [REMS Admin](https://github.com/mcode/rems-admin), [REMS SMART on FHIR app](https://github.com/mcode/rems-smart-on-fhir), and [Pharmacy System](https://github.com/mcode/pims).

Any compliant FHIR server can replace this server in the workflow.  This EHR server is provided as a convenience. All apps in the REMS workflow are programmed to function with any FHIR server, but are programmed to work with this one by default.

## Initialization

1. Install [Java](https://www.oracle.com/java/technologies/downloads/) version 11 or higher.
2. Install [gradle](https://gradle.org/) version 6.9 or higher.
3. Run `gradle bootRun`
4. In a separate terminal tab, run `gradle loadData` to load resources.

This will start the server running on http://localhost:8080/test-ehr.

**If you've loaded resources before, and want a clean slate, delete the `target` and `build` folders from test-ehr if they exist.**

## Using OAuth security features

The FHIR server is open by default, but this can be changed in the `application.yml` file.

First, change the `use_oauth` flag to `true` to turn on security. Then set the `client_id`, `client_secret`, and `oauth_token` fields.

If using Keycloak and following the [REMS Admin](https://github.com/mcode/rems-admin) guide, the `client_id` and `oauth_token` fields can be left as default. The `client_secret` can be found with the following steps:

1. Open the Keycloak admin console (http://localhost:8180/auth) and log in
2. Open the ClientFhirServer, then the `clients` tab, and click `app-token`.
3. Click on the `Credentials` tab, use the `regenerate secret` option if needed.
4. Copy the client secret into the `application.yml` file under `client_secret`

## Usage

The EHR mainly serves resources to clients and handles authorization requests. Generally, the other apps in the REMS workflow interact with the EHR automatically. To add resources to the EHR, place the resource files in the `fhirResourceToLoad` directory and then run `gradle loadData` while the server is currently running.

A new resource can also be created manually by making a POST to a resource endpoint, for example:

`curl -d '{"resourceType":"Patient", "id":"example"}' -H "Content-Type: application/json" -X POST http://localhost:8080/test-ehr/r4/Patient`

## SMART on FHIR

The EHR server SMART on FHIR compliant. This means that SMART apps like the [Request Generator](https://github.com/mcode/request-generator) and [REMS SMART on FHIR app](https://github.com/mcode/rems-smart-on-fhir) can authenticate automatically against the FHIR server.

All authorization requests are proxied to an authorization server. Any OAuth2.0 compliant authorization server can be connected to the EHR by changing the `auth_base` property in the `application.yml` file. 

This server provides a `/_services/smart/Launch` endpoint for session creation. A client POSTs a payload consisting of a `launchUrl`, `redirectUri`, and `parameters`, and receives a `launchId`.

The `launchId` is a unique ID which lets the SMART app maintain its state through the authorization process. This is not a standard process. Apps like the [Request Generator](https://github.com/mcode/request-generator), which are within the EHR ecosystem but not directly connected, might make use of this endpoint to create SMART sessions and launch SMART apps.

## Server endpoints

| Relative URL   | Endpoint Description                                   |
| -------------- | ------------------------------------------------------ |
| `/test-ehr/`   | Base server endpoint                                   |
| `/test-ehr/r4` | EHR FHIR Server endpoint (will not resolve in browser) |
| `/test-ehr/script/rxfill` | NCPDP SCRIPT endpoint that RxFill messages from the pharmacy can be sent to (will not resolve in browser) |
| `/_services/smart/Launch` | SMART on FHIR endpoint which produces a launch ID for SMART apps to use when launching |

# Data Rights

<div style="text-align:center">
<b>NOTICE</b>
</div>

This (software/technical data) was produced for the U. S. Government under Contract Number 75FCMC18D0047/75FCMC23D0004, and is subject to Federal Acquisition Regulation Clause 52.227-14, Rights in Data-General.


No other use other than that granted to the U. S. Government, or to those acting on behalf of the U. S. Government under that Clause is authorized without the express written permission of The MITRE Corporation.


For further information, please contact The MITRE Corporation, Contracts Management Office, 7515 Colshire Drive, McLean, VA 22102-7539, (703) 983-6000.

<div style="text-align:center">
<b>&copy;2025 The MITRE Corporation.</b>
</div>

<br />

Licensed under the Apache License, Version 2.0 (the "License"); use of this repository is permitted in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

