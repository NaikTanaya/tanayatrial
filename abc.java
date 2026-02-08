GitHub JDK Upgrade Agent Skills

Overview

This document defines the skills and responsibilities for a Claude-based GitHub Agent that performs JDK upgrade validation and updates on a given repository using an existing GitHub MCP server.

The agent operates on a patch branch of a repository provided by the user and performs a series of automated checks and updates related to versioning, metadata validation, and configuration consistency.


---

Prerequisites

GitHub MCP server is already created and accessible

The agent has read/write access to the target repository

Repository follows standard Maven and deployment configuration conventions



---

Input

The user provides:

GitHub repository name (e.g., org/example-service)



---

High-Level Workflow

1. Access the provided GitHub repository


2. Locate the patch branch


3. Perform JDK upgrade–related checks and updates


4. Validate required configuration files


5. Report results (changes made or issues found)




---

Detailed Responsibilities

1. Patch Branch Discovery

Identify and switch to the repository’s patch branch

If multiple patch branches exist, select the most recent one

Fail gracefully if no patch branch is found



---

2. Maven Version Upgrade (pom.xml)

2.1 Parent Version Increment

Locate pom.xml

Identify the parent version

Increment the version by +1 patch level


Example:

1.0.1 → 1.0.2


2.2 Version Consistency Update

Search the entire repository for the old version string

Replace all occurrences of the old version with the new version



---

3. API Metadata Validation (api.meta)

3.1 File Presence Check

Verify whether an api.meta file exists in the patch branch

If missing, report as an error


3.2 Region-Based ID Validation

Determine the API region based on its location or naming, then ensure the correct ID is present in api.meta.

Region → Required ID Mapping

Regions requiring 6816766:

zz

gb

ahe

qa

am

mt

gr

bh


Regions requiring 8421453:

hk

mo

nz

au

in

vn

my

lk

sg

bm

ph

je

bd

id


All other regions:

Must contain 11366934


3.3 Validation Rules

Ensure only the correct ID exists

Flag mismatches or missing IDs



---

4. app.yaml Configuration Validation

Locate app.yaml in the patch branch and verify the following entries line by line.

Required Configuration

serviceAccountID: "CBIL-SVC-JENKINS-ID"

mavenArgs: "-Pshp"

nexusIQIncludeVersion: true

platformImageTag: '3.9.4-20230418'

platformImage: "openjdk"

javaVersion: "jdk8"

Validation Rules

Each key must be present

Values must match exactly

Report missing or incorrect entries



---

Output Behavior

The agent should:

Apply updates automatically where safe (pom.xml version changes)

Validate and report on configuration issues

Provide a clear summary including:

Files modified

Checks passed

Errors or mismatches found




---

Failure Handling

If critical files (pom.xml, api.meta, app.yaml) are missing:

Do not proceed silently

Report the issue clearly


If version parsing fails:

Abort version updates

Notify the user




---

Non-Goals

No JDK binary upgrades

No branch creation or merging

No CI/CD pipeline execution



---

Summary

This GitHub Agent ensures consistent, region-aware JDK upgrade compliance by validating Maven versions, API metadata, and deployment configuration in a repository’s patch branch, reducing manual effort and configuration drift.