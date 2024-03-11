# Spring Framework Example

This project is an example of a Spring Framework application.

## Schema

#### Main Entities

* **Conference**
  * **Attributes:**
    * `id`: Unique identifier of the conference.
    * `name`: Name of the conference.
  * **Records:** 1,920 records

* **Edition**
  * **Attributes:**
    * `id`: Unique identifier of the edition.
    * `ref_Conference`: Identifier of the associated conference.
    * `year`: Year of the edition.
    * `date`: Date of the edition.
    * `country`: Country of the edition.
  * **Records:** 9,600 records

* **Researcher**
  * **Attributes:**
    * `id`: Unique identifier of the researcher.
    * `name`: Name of the researcher.
    * `surname`: Surname of the researcher.
    * `secSurname`: Second surname of the researcher.
    * `university`: University of the researcher.
  * **Records:** 500,000 records

* **Article**
  * **Attributes:**
    * `id`: Unique identifier of the article.
    * `title`: Title of the article.
    * `ref_Edition`: Identifier of the associated edition.
  * **Records:** 9,110,314 records

* **Author**
  * **Attributes:**
    * `id`: Unique identifier of the author.
    * `ref_Article`: Identifier of the associated article.
    * `ref_Researcher`: Identifier of the associated researcher.
  * **Records:** 45,561,272 records

## Contact

For any queries or concerns (even tips to improve this example), please reach out to:

Name: V. Rojas Aranda
Email: vrojasaranda@gmail.com

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details