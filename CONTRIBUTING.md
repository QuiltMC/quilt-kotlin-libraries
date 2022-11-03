# Contributing
This documents outlines best-practices and contributing guidelines to the Quilt Kotlin Libraries

## Guide: Pull Requests

1. ### Discuss your feature
   Be sure to talk to us (either through an issue or on [Discord](https://discord.quiltmc.org/toolchain)) before working on your feature! We can help you with any questions you may have, and save some time during the review process.
2. ### Open your PR and wait for reviews
   Once you have forked QSL and opened a pull request, you need to wait for people to review it. When you get reviews, try to thoughtfully address any concerns other people have. If you get confused, be sure to ask questions!
3. ### Entering a Final Comment Period
   Once your PR has no "changes requested" reviews, the minimum number of approvals for its [triage category](#guide-triage-categories), and nobody has their review requested, it is eligible to enter a Final Comment Period (FCP). A FCP is a last call for any reviewers to look at your PR before it is merged. The minimum length of your PR's FCP is determined by its triage category, but if any further changes are requested, the FCP might be lengthened, or if the concerns are significant, the FCP cancelled until the concerns are resolved.
4. ### Request a merge!
   Once the minimum time on the Final Comment Period has passed, and you have resolved any concerns reviewers have raised during that time, leave a comment on your PR requesting for it to be merged. A QSL Core Team member will take a final look over your PR, and if everything looks good, merge it!


## Guide: Triage Categories

Triage categories ensure that important, but small PRs -- like bugfixes -- are merged quickly, while large changes -- like new wrappers -- are thoroughly reviewed before they are merged.

PRs to QKL are currently defined their effect on the project. If the PR provides a new module or rewrites some code it is labelled appropriately and left. If the PR is a small bugfix, or typo fix PR, it is given the `fast-track` label, allowing it to be merged as soon as each team member has given their approving reivew.

PRs to QKL require approval from every team member before being put into final comment. This is subject to change in the future.

Currently, the final comment period for all PRs is set at 3 days. In the future, extra categories for PRs may be created, each with specific FCP times. 