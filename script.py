import requests
from bs4 import BeautifulSoup
from notion_client import Client

# Notion API Setup
NOTION_TOKEN = "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"
DATABASE_ID = "14c926a218098027b001d44694f762a4"

notion = Client(auth=NOTION_TOKEN)

# Function to scrape Google search links
def scrape_google_links(query):
    headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
    search_url = f"https://www.google.com/search?q={query.replace(' ', '+')}"
    
    response = requests.get(search_url, headers=headers)
    soup = BeautifulSoup(response.text, "html.parser")
    
    # Extract top 5 search result links
    links = []
    for a in soup.select("a"):
        href = a.get("href")
        if href and href.startswith("/url?q="):
            link = href.split("/url?q=")[1].split("&")[0]
            
            # Filter out unwanted links (e.g., Google Maps or invalid URLs)
            if "maps.google.com" in link or "google.com/maps" in link:
                continue  # Skip Google Maps links
            if not link.startswith("http"):  # Skip non-HTTP links
                continue
            
            links.append(link)
            
            # Stop after collecting 5 links
            if len(links) == 5:
                break
    return links

# Function to find a Notion page by its name
def find_page_by_name(page_name):
    try:
        query = notion.databases.query(
            database_id=DATABASE_ID,
            filter={
                "property": "Name",
                "title": {
                    "equals": page_name
                }
            }
        )
        results = query.get("results", [])
        return results[0] if results else None
    except Exception as e:
        print(f"Error finding page '{page_name}': {e}")
        return None

# Function to add links to a page as clickable mentions
def add_links_to_page(page_id, links):
    try:
        for link in links:
            notion.blocks.children.append(
                page_id,
                children=[
                    {
                        "object": "block",
                        "type": "paragraph",
                        "paragraph": {
                            "rich_text": [
                                {
                                    "type": "text",
                                    "text": {
                                        "content": link,  # Visible text
                                        "link": {"url": link},  # Clickable link
                                    },
                                }
                            ]
                        },
                    }
                ],
            )
        print(f"Links added to page {page_id} as clickable mentions.")
    except Exception as e:
        print(f"Error updating page: {e}")

if __name__ == "__main__":
    # Define resource categories and their search queries
    resources = {
        #"Courses": " ",
        #"YouTube Videos": " ",
        #"PDFs": " ",
        #"geeks for geeks": "geeks for geeks: Null Safety in Kotlin",
        #"Podcasts": " ",
        #"Apps": "play.google.com: Null Safety in Kotlin",
        #"Tiktok":  ",
        #"CSDN": "CSDN.net: Kotlin 中的空安全",
        #"Twitter": " ",
        #"Medium" : "Medium.com: Null Safety in Kotlin"
    }

    for page_name, query in resources.items():
        print(f"Processing page: {page_name}")
        # Find the corresponding page in Notion
        page = find_page_by_name(page_name)
        if not page:
            print(f"Page '{page_name}' not found in database.")
            continue

        # Scrape Google search links
        links = scrape_google_links(query)
        print(f"Top 5 links for '{page_name}': {links}")

        # Add links to the found page
        add_links_to_page(page["id"], links)
